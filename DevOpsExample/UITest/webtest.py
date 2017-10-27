from selenium import webdriver
from pyvirtualdisplay import Display
from selenium.webdriver.common.keys import Keys
import os

display = Display(visible=0, size=(800, 600))
display.start()

driver = webdriver.Firefox()
url = os.environ.get('devops_endpoint','http://localhost:8080/WebExample/')
driver.get(url)

html = driver.page_source
assert "Name" in html

print("selenium test completed!")

driver.close()