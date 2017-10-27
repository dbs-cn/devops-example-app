from selenium import webdriver
from pyvirtualdisplay import Display
from selenium.webdriver.common.keys import Keys
import os

print("start selenium test.......")
display = Display(visible=0, size=(800, 600))
display.start()

driver = webdriver.Firefox()
url = os.environ.get('devops_endpoint','http://localhost:8000/WebExample/')
driver.get(url)

html = driver.page_source

print("test case 1 : assert Name exists")
assert "Name" in html

print("test case 2 : assert Balance exists")
assert "Balance" in html

print("test case 3 : assert Number exists")
assert "Number" in html

print("test case 4 : assert Is VIP exists")
assert "Is VIP" in html

print("selenium test completed!")

driver.close()