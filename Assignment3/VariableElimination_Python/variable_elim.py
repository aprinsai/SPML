"""
@Author: Joris van Vugt, Moira Berens

Implementation of the variable elimination algorithm for AISPAML assignment 3

"""
from itertools import permutations
import pandas as pd

class VariableElimination():

    def __init__(self, network):
        self.network = network
        self.addition_steps =  0
        self.multiplication_steps = 0

    def run(self, query, observed, elim_order):
        """
        Use the variable elimination algorithm to find out the probability
        distribution of the query variable given the observed variables

        Input:
            query:      The query variable
            observed:   A dictionary of the observed variables {variable: value}
            elim_order: Either a list specifying the elimination ordering
                        or a function that will determine an elimination ordering
                        given the network during the run

        Output: A variable holding the probability distribution
                for the query variable

        """
        # What is the product formula
        # The reduced formula based on network structure? -- Is already given in the dataset
        # Identify factors and reduce observed variables -- Sorta done needs flexibility
        # Fix an elimination ordering -- Done
        # For every variable in elim_order:
            # Multiply factors containing that variable
            # Sum out the variable to obtain new factor
            # Remove the multiplied factors from the list and add the summed out factor
        # Normalize. 
        
        
        # Dictionary is the reduced formula of factors. 
        # Reducing oberved variable = eliminate all values with incorrect observed value.
                
        # Reduce observed.
        probabilities = self.network.probabilities
        newProb = []
        for keyO, valueO in observed.items():
            for keyP, prob in probabilities.items():
                if keyO in list(prob.columns.values):
                    # Now it's hardcoded as Earthquake.
                    # How to index df by variable name?
                    newProb = prob[prob.get(keyO) == valueO]
                    probabilities[keyP] = newProb
            
            
       # print probabilities
        dflist = []
        for elim in elim_order:
            for key1, prob1 in probabilities.items():
                for key2, prob2 in probabilities.items():
                    if elim in list(prob1.columns.values) and elim in list(prob2.columns.values) :
                        if key1!=key2:
                            #print("elim:", elim)
                            columnvalues1 = list(prob1.columns.values)
                            columnvalues2 = list(prob2.columns.values)
                            columnvalues = list(set(columnvalues1).intersection(columnvalues2))
                            columnvalues.remove('prob')
                            dflist.append(pd.merge(prob1, prob2, on=columnvalues, suffixes=('_1', '_2')))
                        else:
                            dflist.append(prob1)
        print dflist
        #product_formula = sum(self.network.probabilities)
        
        

