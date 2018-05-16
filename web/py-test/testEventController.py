
import json
from requests import get, Session

baseUrl = 'http://localhost:8080/'

session = Session()

# login

rep = session.get(baseUrl + 'user/validate?account=admin&password=123')
if json.loads(rep.content)['code'] != 200:
    raise Exception(rep.content)

r = session.get(baseUrl + 'event/getLatest')
print(r.status_code)
print(r.content)