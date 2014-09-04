#!/usr/env python
import json
import sys
import re
import urllib 
from bs4 import BeautifulSoup

SITE = 'http://finance.yahoo.com'

def child(node) :
  return node.children.next()

def get_contents(node) :
  # Sweet goodness this is hacky
  # but I kind of like it :)
  # I imagine the exception handling overhead
  # isn't too great either
  contents = node.contents
  try :
    return get_contents(child(node))
  except :
    return contents

def contractAsJson(filename):
  soup = BeautifulSoup(open(filename))  
  json_data = {}

  # scrape current price of the stock
  rtq_ticker = soup.find(class_='time_rtq_ticker')
  curr_price = float(rtq_ticker.children.next().contents[0])
  json_data['currPrice'] = curr_price
  
  # scrape urls of other expiration days
  expiration_re = re.compile('.*&m=\d{4}-\d{2}')
  expiration_top = soup.find('tr', valign='top').children.next()
  expiration_nodes = expiration_top.find_all(href=expiration_re)
  expirations = [('%s%s' % (SITE, exp['href'])).replace('&', '&amp;') 
                 for exp in expiration_nodes]

  json_data['dateUrls'] = expirations 

  # sort options by decreasing order of open interest
  option_tag = re.compile('(yfnc_h)|(yfnc_tabledata1)')
  options = sorted(
    get_contract_data(soup, option_tag),
    key=lambda o : int(o['Open'].replace(',','')),
    reverse=True) 
  
  json_data['optionQuotes'] = options
  json_data = json.dumps(json_data,indent=4, sort_keys=True, separators=(',', ': '))
  return json_data

def get_contract_data(soup, tag) :
  i = 0
  
  curr_option = {}
  options = []
  
  bad_contract_re = re.compile('.*#(\w+);.*>(.*)<.*')
  contract_re = re.compile('([a-zA-Z]+.*)(\d{6})([a-zA-Z]).*')

  for contract_node in soup.find_all(class_=tag) :
    # Fetch Strike
    if i == 0 :
      curr_option['Strike'] = get_contents(contract_node)[0]

    # Fetch Date/Symbol/Type
    elif i == 1 :
      contents = get_contents(contract_node)[0]
      contents_m = contract_re.match(contents)
      curr_option['Symbol'] = contents_m.group(1)
      curr_option['Date'] = contents_m.group(2)
      curr_option['Type'] = contents_m.group(3)
      
    # Fetch Last
    elif i == 2 :
      curr_option['Last'] = get_contents(contract_node)[0]

    # Fetch Change
    elif i == 3 :
      contents = get_contents(contract_node)
      contents_m = bad_contract_re.match(str(contents[1]))
      content_color = contents_m.group(1)
      content_val = contents_m.group(2)

      if content_color == 'cc0000' :
        content_val = '-%s' % content_val
      elif content_color == '008800' :
        content_val = '+%s' % content_val
      else : #color == '000000'
        content_val = ' %s' % content_val

      curr_option['Change'] = content_val

    # Fetch Bid
    elif i == 4 :
      curr_option['Bid'] = contract_node.contents[0]

    # Fetch Ask
    elif i == 5 :
      curr_option['Ask'] = contract_node.contents[0]
    
    # Fetch Vol
    elif i == 6 :
      curr_option['Vol'] = contract_node.contents[0]
    
    # Fetch Open
    elif i == 7 :
      curr_option['Open'] = contract_node.contents[0]

    if i == 7 :
      options += [curr_option]
      curr_option = {}
      i = 0
    else :
      i += 1
  return options
 
if __name__ == '__main__' :
  print contractAsJson(sys.argv[1])
