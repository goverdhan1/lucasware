package com.lucas.services.util;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionsUtilService {
	
	protected static Logger LOG = LoggerFactory.getLogger(CollectionsUtilService.class);

	@SuppressWarnings("unchecked")
	public static  <T extends Iterable<?>> T nullGuard(T item) {
		  if (item == null) {
		    return (T) Collections.EMPTY_LIST;
		  } else {
		    return item;
		  }
    }
	
	/**
	 * Compares two multiMaps for equality by comparing the quality and cardinality of keys and values.
	 * @param map1
	 * @param map2
	 * @return
	 */
    public static boolean compareMultiMaps(MultiMap map1, MultiMap map2){
    	boolean boo = false;
    	if (map1 == null || map2 == null){
    		LOG.debug("One or both of the compared multi maps is null");
    		boo = false;
    	} else if (map1.size() != map2.size()){
    		LOG.debug("There are different number of key elements in the compared multi maps. Map1: " + map1.size() + ", Map2: " + map2.size());
    			boo = false;
    	} else {
    		Set<String> keySet1 = map1.keySet();
    		Set keySet2 = map2.keySet();
    		Collection diff = CollectionUtils.disjunction(keySet1, keySet2);
    		if (diff == null || diff.size() == 0){
    			//no diff in keys
        		for (Object key : keySet1) {
    				Collection c1 = (Collection)map1.get(key);
    				Collection c2 = (Collection)map2.get(key);
                    final Collection ct1=new ArrayList();
                    final Collection ct2=new ArrayList();
    				if (c1 != null && c2 != null) {
    					if (c1.size() == c2.size()){
                            final Iterator iterator1=c1.iterator();
                            while(iterator1.hasNext()){
                                Object object=iterator1.next();
                                if(object instanceof String){
                                    ct1.add(object);
                                }else if (object instanceof List){
                                    ct1.add(((List)object).get(0));
                                }
                            }
                            final Iterator iterator2=c2.iterator();
                            while(iterator2.hasNext()){
                                Object object=iterator2.next();
                                if(object instanceof String){
                                    ct2.add(object);
                                }else if (object instanceof List){
                                    ct2.add(((List)object).get(0));
                                }
                            }
		    				diff = CollectionUtils.disjunction(ct1,  ct2);

		    				if (diff == null || diff.size() == 0){
		    					boo = true;
		    				} else {
		    			    	LOG.debug("Difference in collection for key :" + key.toString());
		    			    	boo = false;
		    					CollectionsUtilService.dumpCollection(diff);
		    					break;
		    				}
    					} else {
    						boo = false;
    						LOG.debug("Collections for key: " + key + " are unequal in size. C1 size: " + c1.size() + ", C2 size: " + c2.size());
    						break;
    					}
    				} else {
    					boo = false;
    					LOG.debug("One or both collections for key: " + key.toString() + " are null");
    					break;
    				}
    			}
    		} else {
    			LOG.debug("Difference in keyset size");
    			dumpCollection(diff);
    		}

    	}
    	return boo;
    }
    
    /**
     * Dumps collection values to confugured logger
     * @param c
     */
    public static void dumpCollection(Collection c){
    	for (Object object : c) {
			LOG.debug(object.toString()); LOG.debug("====");
		}
    }
    
    /**
     * Dumps MultiMap key and the number of the values in the corresponding collection to the configured logger.
     * @param mm
     */
    public static void dumpMultiMapNumbers(MultiMap mm){
    	for (Object o : mm.keySet()) {
			Collection c = (Collection)mm.get(o);
			LOG.debug("Key: " + o.toString() + ", Number of values: " + (c == null?0:c.size()));
		}
    }

    /**
     * Returns true if the collection is null or empty
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(Collection<?> collection){
       return (collection == null? true:collection.isEmpty());
    }

    /**
     * Returns true if the map is null or has no entries.
     * @param map
     * @return
     */
    public static boolean isNullOrEmpty(Map<?,?> map){
        return (map == null? true:map.isEmpty());
    }

}
