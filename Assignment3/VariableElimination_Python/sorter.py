# -*- coding: utf-8 -*-
"""
Created on Wed May 16 19:38:58 2018

@author: Pleun
"""

class Sorter():
    """
    This class is for sorting.
    """
    def sort(nodes, parents):   
        pList = []
        parent = list(parents.keys())
        for p in parent:
            pList.append(p)
        # Remove duplicates
        pList = list(set(pList))
        sortedList = [x for x in nodes if x in pList]
        
        return sortedList
        #First leaf nodes, then 