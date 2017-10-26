from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import os

driver = webdriver.Firefox()
url = os.environ.get('devops_endpoint','http://localhost:8080/WebExample/')
driver.get(url)

html = driver.page_source
assert "Name" in html

print("selenium test completed!")

driver.close()