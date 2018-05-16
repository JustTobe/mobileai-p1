from requests import get

baseUrl = 'http://localhost:8080/'

r = get(baseUrl + 'user/validate?account=admin&password=123')
print(r.status_code)
print(r.content)

# get check code
url = baseUrl + 'usr/getCheckCode'
r = get(url)
# r.content