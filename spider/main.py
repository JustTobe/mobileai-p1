
import requests   
proxies = { "http": "http://localhost:1080", "https": "http://localhost:1080", }   
requests.get("http://google.org", proxies=proxies)