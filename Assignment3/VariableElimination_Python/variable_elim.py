"""
@Author: Joris van Vugt, Moira Berens

Implementation of the variable elimination algorithm for AISPAML assignment 3

"""
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
            # Multiply factors containing that variable -- Done 
            # Sum out the variable to obtain new factor -- 
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
                    newProb = prob[prob.get(keyO) == valueO]
                    probabilities[keyP] = newProb
        
        # Prepare a list of all the factors containing certain variables.
        productList = []
        for key, prob in probabilities.items():
            productList.append(prob)
        
        for elim in elim_order:
            multList = []
            for probDist in productList:
                if elim in list(probDist.columns.values):
                    multList.append(probDist)
            
            multList = reduce(lambda x,y: x.merge(y, on = elim, suffixes=('_1', '_2')), multList)
            
            # Multiply
            multList['newProb'] = multList.apply(lambda row: (row['prob_1']*row['prob_2'] if 'prob_1' in list(multList.columns.values) and 'prob_2' in list(multList.columns.values) else row['prob']), axis = 1)
            self.multiplication_steps += 1
            
            # Clean up
            if 'prob' in list(multList.columns.values):
                multList.drop('prob', axis = 1, inplace = True)
            if 'prob_1' in list(multList.columns.values) and 'prob_2' in list(multList.columns.values):
                multList.drop('prob_1', axis = 1, inplace = True)
                multList.drop('prob_2', axis = 1, inplace = True)
            
            multList.rename({'newProb':'prob'}, axis = 1, inplace = True)
            
            # Summing
            colValues = list(multList.columns.values)
            colValues.remove(elim)
            colValues.remove('prob')
            sum_bodyoncetoldme = pd.DataFrame(multList.groupby(colValues).sum().reset_index())
            self.addition_steps += 1
            
            # Update productList
            productList = [factor for factor in productList if elim not in list(factor.columns.values)]
            productList.append(sum_bodyoncetoldme)
        
        # Last multiplication of the query variable. 
        productList = reduce(lambda x,y: x.merge(y, on = query, suffixes = ['_1','_2']), productList)
        
        probFalse = 1.0
        for col in productList.iloc[0]:
            if isinstance(col, float):
                probFalse = probFalse * col
        probTrue = 1.0
        for col in productList.iloc[1]:
            if isinstance(col, float):
                probTrue = probTrue * col
        productList.loc[1,'finalProb'] = probTrue
        productList.loc[0,'finalProb'] = probFalse
        self.multiplication_steps += 1
        
        # Clean up
        if 'prob' in list(productList.columns.values):
            productList.drop('prob', axis = 1, inplace = True)
        if 'prob_1' in list(productList.columns.values) and 'prob_2' in list(productList.columns.values):
            productList.drop('prob_1', axis = 1, inplace = True)
            productList.drop('prob_2', axis = 1, inplace = True)
        
        # Normalize
        normalizing = productList['finalProb'].sum()
        
        for index, row in productList.iterrows():
            productList.loc[index,'finalProb'] = row['finalProb']/normalizing
        
        #print productList
        
        productList = pd.DataFrame(productList.groupby(query).sum().reset_index())
        
        print "Final: {}\n\n  Additions: {}\n  Multiplications: {}".format(productList, self.addition_steps, self.multiplication_steps)