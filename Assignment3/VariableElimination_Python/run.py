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
        print "pList: {}".format(pList)
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
    
    # the class BayesNet represents a Bayesian network from a .bif file
    # in several variables
    net = BayesNet('earthquake.bif') 
    
    # these are the variables that should be used for variable elimination
#    print 'values', net.values 
#    print 'probabilities', net.probabilities
    print 'parents', net.parents
#    print 'nodes', net.nodes
    
    # Create object
    ve = VariableElimination(net)
    # Observed variables
    observed = {}
    
    # Query
    query = 'Alarm'
    
    # Elimination order
    elim_order = sort(net.nodes, net.parents, query)
    print elim_order
    
     
    
    # Make your variable elimination code in a seperate file: 'variable_elim'. 
    # you can call this file as follows:
    #ve = VariableElimination(net)
    
    # If variables are known beforehand, you can represent them in the following way: 
    # evidence = {'Burglary': 'True'}

    # determine your heuristics before you call the run function. This can be done in this file or in a seperate file
    # The heuristics either specifying the elimination ordering (list) or it is a function that determines the elimination ordering
    # given the network. An simple example is: 
    # elim_order = net.nodes

	#call the elimination ordering function for example as follows:   
    #ve.run('Alarm', evidence, elim_order)

    
 
