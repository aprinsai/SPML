"""
@Author: Joris van Vugt, Moira Berens

Entry point for testing the variable elimination algorithm

"""
from read_bayesnet import BayesNet
from variable_elim import *

if __name__ == '__main__':
    
    def sort(nodes, parents, query):  
        # Make list of all parents
        pList = []
        parent = list(parents.values())
        for p1 in parent:
            for p2 in p1:
                pList.append(p2)
        # Remove duplicates
        pList = list(set(pList))
        # Add all leafs first. 
        leafNodes = [x for x in nodes if x not in pList]
        
        # Add to sorted list
        sortedList = []
        sortedList.extend(leafNodes)
        sortedList.extend(pList)
 
        # Remove query and return
        if query in sortedList:
            sortedList.remove(query)
        return sortedList
    
    # the class BayesNet represents a Bayesian network from a .bif file in several variables
    net = BayesNet('cancer.bif')
    
    # Create object
    ve = VariableElimination(net)
    
    # Observed variables
    observed = {}
    
    # Query
    query = 'Cancer'
    
    # Elimination order
    elim_order = sort(net.nodes, net.parents, query)
    
    ve.run(query, observed, elim_order)