# No need to process files and manipulate strings - we will
# pass in lists (of equal length) that correspond to 
# sites views. The first list is the site visited, the second is
# the user who visited the site.

# See the test cases for more details.

def highest_affinity(site_list, user_list, time_list):
  # Returned string pair should be ordered by dictionary order
  # I.e., if the highest affinity pair is "foo" and "bar"
  # return ("bar", "foo").
  
  #Find pair of pages that have both been seen by
  # the most people
  
  paircount = {}
  usermap = {}

  for i in xrange(len(site_list)) :
    site = site_list[i]
    user = user_list[i]
    
    # if the user has seen another site as well as
    # this site, add it to the pair count.
    # Although, first check if this site has already
    # been seen by the user. If it has, then
    # that means this is not a new pair, so do not add it
    
    if user in usermap and site in usermap[user] :
      continue
    elif user not in usermap :
      usermap[user] = []
    
    # Now go through that user's sites
    # For each site, add a pair with that site and the new one
    # to the paircount. If it already exists, add one
    # else instantiate map key with val 1
    
    for site_p in usermap[user] :
      # calculate pair here (a, b)
      pair = (site, site_p)
      if (site_p < site) :
        pair = (site_p, site)
      
      if pair in paircount :
        paircount[pair] += 1
      else :
        paircount[pair] = 1
     
    usermap[user] += [site]   
  
  # Iterate over the pair counts and find the one with the highest count
  maxpair = ''
  maxpaircount = -1
  for pair, count in paircount.iteritems() :
    if count > maxpaircount :
      maxpaircount = count
      maxpair = pair
    
  return maxpair

