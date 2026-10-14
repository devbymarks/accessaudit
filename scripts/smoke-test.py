#!/usr/bin/env python3
"""Smoke test do AccessAudit em execução. Uso: python scripts/smoke-test.py [base_url]"""
import json, sys, time, urllib.request, urllib.error, http.cookiejar
BASE = (sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080").rstrip("/")
jar = http.cookiejar.CookieJar()
opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(jar))
def call(path, method="GET", payload=None, expected=(200,)):
    data = None if payload is None else json.dumps(payload, ensure_ascii=False).encode()
    req = urllib.request.Request(BASE + path, data=data, method=method, headers={"Content-Type":"application/json"})
    try:
        with opener.open(req, timeout=10) as res:
            body = res.read().decode()
            if res.status not in expected: raise RuntimeError(f"{method} {path}: HTTP {res.status}: {body}")
            return json.loads(body) if body else None
    except urllib.error.HTTPError as exc:
        body=exc.read().decode()
        if exc.code in expected: return json.loads(body) if body else None
        raise RuntimeError(f"{method} {path}: HTTP {exc.code}: {body}") from exc

def main():
    print(f"AccessAudit smoke test: {BASE}")
    auth=call('/api/auth/login','POST',{'username':'admin','password':'AccessAudit@2026'})
    assert auth['role']=='ADMIN', 'Login não retornou ADMIN'
    print('OK - login e sessão')
    dashboard=call('/api/dashboard')
    for key in ('total','pending','activeAccesses','rejected','reviewAlerts'): assert key in dashboard, f'Métrica ausente: {key}'
    print('OK - dashboard')
    users=call('/api/users'); systems=call('/api/systems'); profiles=call('/api/profiles')
    assert users and systems and profiles, 'Catálogos iniciais vazios'
    print(f'OK - catálogos ({len(users)} usuários, {len(systems)} sistemas, {len(profiles)} perfis)')
    stamp=str(time.time_ns())
    user=call('/api/users','POST',{'fullName':f'Smoke Test {stamp}','email':f'smoke-{stamp}@example.test','department':'QA','jobTitle':'Conta de teste','active':True})
    profile=next((p for p in profiles if p['active']), profiles[0])
    request=call('/api/requests','POST',{'requester':user['fullName'],'email':user['email'],'systemName':profile['systemName'],'accessRole':profile['name'],'requestType':'GRANT','justification':'Smoke test automatizado do AccessAudit.'},(201,))
    rid=request['id']; assert request['status']=='PENDING'
    print(f'OK - criação da solicitação #{rid}')
    decided=call(f'/api/requests/{rid}/decision','POST',{'decision':'APPROVED','reviewer':'Smoke Test','note':'Aprovado pelo teste automatizado.'})
    assert decided['status']=='APPROVED' and decided.get('reviewDueAt'), 'Aprovação não programou revisão'
    print('OK - aprovação e agendamento de revisão')
    events=call(f'/api/requests/{rid}/audit'); assert len(events)>=2
    print('OK - trilha de auditoria da solicitação')
    accesses=call('/api/accesses'); grant=next(g for g in accesses if g['sourceRequestId']==rid)
    call(f"/api/accesses/{grant['id']}/review",'POST',{'actor':'Smoke Test','note':'Revisão de teste concluída.'})
    print('OK - conclusão de revisão')
    call('/api/auth/logout','POST',{})
    print('\nRESULTADO: fluxo básico validado com sucesso.')
if __name__=='__main__':
    try: main()
    except Exception as exc:
        print(f'FALHOU: {exc}', file=sys.stderr); sys.exit(1)
