"""
@Author: Joris van Vugt, Moira Berens

Implementation of the variable elimination algorithm for AISPAML assignment 3

"""

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
                    newProb = prob[prob.Earthquake == valueO]
                    probabilities[keyP] = newProb
            
            
        print probabilities
        
#        for var in elim_order:
#            for keyP, prob in probabilities.items():
#                if var in list(prob.columns.values):
                    #print prob
                  
        #product_formula = sum(self.network.probabilities)
        
        

