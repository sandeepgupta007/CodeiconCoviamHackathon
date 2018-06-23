''' 
	Genetic Algorithm to predict to the most optimal solution time for interview.

	Q. Why this ?
	Ans. If done in brute force, all the interview will either be assigned to a single interviewer or 
	  interviews are assigned such that one person is taking interview in the morning, and the second interview in the evening 
	  to avoid such uneveness in preditcting the time slots for all the interviews.

	  @sandeepgupta
'''
import random
import sys
import bisect

# Reading from input file
sys.stdin = open('in','r')

#Global vairables
HR = {}
CANDIDATE = {}
CANDIDATE_COUNT = 0
HR_COUNT = 0
DAYS = 0
ONE_INTERVIEW_TIME = 1 
CHROMOSOME_SIZE = 0
POPULATION_SIZE = 100
RATE = 0.1
NO_OF_ITERATION = 100
TOTAL_SLOTS_OF_HR = 0

# To generate initial Population
def generatePopulation():
	if(TOTAL_SLOTS_OF_HR < CANDIDATE_COUNT):
		print("Not Sufficient HR Available")
		exit()

	population = []
	for i in range(POPULATION_SIZE):
		initialChromosome =  DNA()
		# print (initialChromosome)
		chromosomes = initialCandidateAssignment(initialChromosome)
		population.append(chromosomes)
	return population

''' 
	DNA is used to generate the intial population.
	ONE_INTERVIEW_TIME - Amount of time given to each interview 0.5 for 1/2 hour or for 2 hrs give 2. (Similarly)
	Assuming 1hr interviewTime only. (for now)
	and 13hrs per day taking the day to be from 9AM to 10PM (interview can be taken place.)
'''
def DNA():
	# chromosome = []*day*13
	global CHROMOSOME_SIZE
	CHROMOSOME_SIZE = 0
	chromosome = {}
	# print (DAYS)
	for i in range(int((DAYS+1)*13)):
		chromosome[i] = []
	# print (chromosome)
	#distributing the HR over a time period.
	for key in HR:
		for HRTime in HR[key]:
			day = HRTime[0] #day
			fromTime = HRTime[1] #starttimeCode
			hrPairCandidate = []
			hrPairCandidate.append(key)
			hrPairCandidate.append(-1)
			# print ((day-1)*13 + fromTime)
			eachHourChromosome = chromosome[(day-1)*13 + fromTime]
			eachHourChromosome.append(hrPairCandidate)
			chromosome[(day-1)*13 + fromTime] = eachHourChromosome

	# For memory efficiency - No HR available for this time slot.
	# print (chromosome)
	for i in range((DAYS+1)*13):
		if(len(chromosome[i]) == 0):
			chromosome.pop(i,None)
		else:
			CHROMOSOME_SIZE += 1

	return chromosome

def initialCandidateAssignment(chromosome):
	countOfCandidateAssigned = 0
	candidate = []
	
	#for visited candidate count
	for i in range(CANDIDATE_COUNT): 
		c = []
		c.append(CANDIDATE[i])
		c.append(0)
		candidate.append(c)

	# chromosome key for random selection
	listOfValidChromosome = []
	for key in chromosome:
		listOfValidChromosome.append(key)

	# print (listOfValidChromosome)
	# for initial chromosome assigning HR to candidate randomly
	while (countOfCandidateAssigned < CANDIDATE_COUNT):
		timeSelection = random.choice(listOfValidChromosome)
		# print(chromosome)
		hrSlotSelection = random.randrange(0,len(chromosome[timeSelection]),1)
		candidateSelection = random.randrange(0,CANDIDATE_COUNT,1)
		if(chromosome[timeSelection][hrSlotSelection][1] == -1 and candidate[candidateSelection][1] == 0):
			chromosome[timeSelection][hrSlotSelection][1] = candidateSelection
			candidate[candidateSelection][1]=1
			countOfCandidateAssigned+=1
			# print (chromosome)
	return chromosome

''' 
	Timeslots for a particular session
	Morning : M : 1 : 3 : (9:12)
	Afternoon : A : 4 : 7 : (12:16)
	Evening : E : 8 : 11 : (16:20)
	Night : N : 12 : 13 : (20:21)
'''
def timeFromSession(session):
	listOfFromSession = []
	if(session == 'M'):
		listOfFromSession.append(1)
		listOfFromSession.append(3)
		return listOfFromSession
	elif(session == 'A'):
		listOfFromSession.append(4)
		listOfFromSession.append(7)
		return listOfFromSession
	elif(session == 'E'):
		listOfFromSession.append(8)
		listOfFromSession.append(11)
		return listOfFromSession
	else:
		listOfFromSession.append(12)
		listOfFromSession.append(13)
		return listOfFromSession

#scoring of candidate according to candidateId and timeShift alloted to a particular Candidate
def scoreAccordingCandidate(candidateId,timeShift):
	timeFromSessionValue = timeFromSession(CANDIDATE[candidateId][0])
	if (int(timeFromSessionValue[1]) >= int(timeShift) and int(timeFromSessionValue[0]) <= int(timeShift)):
		return 10

	timeFromSessionValue = timeFromSession(CANDIDATE[candidateId][1])
	if (int(timeFromSessionValue[1]) >= int(timeShift) and int(timeFromSessionValue[0]) <= int(timeShift)):
		return 8

	timeFromSessionValue = timeFromSession(CANDIDATE[candidateId][2])
	if (int(timeFromSessionValue[1]) >= timeShift and int(timeFromSessionValue[0]) <= timeShift):
		return 6

	return 4

# So that all candidate get equal number of interviews to be taken
# considering the fact that all the interviews taken HR are evenly distributed among the HR
''' 
 This function will score +1 for a InterviewTakenByHr lie in the optimal solution(mean value) than +1 score will be added
'''
def scoringAccordingInterviewDistribution(chromosome):
	noOfInterviewsTakenByHr = [0]*HR_COUNT
	for key in chromosome:
		for keyInChromosome in range(len(chromosome[key])):
			if(chromosome[key][keyInChromosome][1] != -1):
				noOfInterviewsTakenByHr[chromosome[key][keyInChromosome][0]] += 1

	meanOfInterview = CANDIDATE_COUNT/HR_COUNT

	score = 0
	for i in noOfInterviewsTakenByHr:
		if(i >= meanOfInterview-1 and i <= meanOfInterview+1):
			score += 1

	return score


# Interviews taken in least possible days, will be given more weightage
def scoringAccordingLeastDays(chromosome):
	score = 0
	day = [0]*DAYS
	for key in chromosome:
		flag = 0
		for keyInChromosome in range(len(chromosome[key])):
			if(chromosome[key][keyInChromosome][1] != -1):
				flag = 1
		if(flag == 1):
			day[key//13] = 1
	
	for i in day:
		if(i == 0):		
			score += 10
	return score


#fitness of full population
def fitnessAll(population):
	listOfFitness = []
	for i in range(len(population)):
		listOfFitness.append(fitness(population[i]))

	return listOfFitness

#fitness of a Chromosome
# A lot to be done for this function.
def fitness(chromosome):
	score = 0
	for key in chromosome:
		for keyInChromosome in chromosome[key]:
			if(keyInChromosome[1] != -1):
				score += scoreAccordingCandidate(keyInChromosome[1],key%13)

	score += scoringAccordingInterviewDistribution(chromosome)
	score += scoringAccordingLeastDays(chromosome)
	return score

# One point Crossover
def crossover(parentA,parentB):
	if(CANDIDATE_COUNT <= 2):
		return (parentA,parentB)

	startCrossOverPoint = random.randrange(0,CANDIDATE_COUNT//2,1)
	crossoverPoint = random.randrange(startCrossOverPoint,CANDIDATE_COUNT,1)
	# print (str(crossoverPoint) + ' ' + str(startCrossOverPoint) +  ' ' + str(parentA) + ' ' + str(parentB))
	for i in range(startCrossOverPoint,crossoverPoint):
		
		timeOfParentA = -1
		keyOfParentA = -1
		timeOfParentB = -1
		keyOfParentB = -1

		for timeId in parentA:
			for j in range(len(parentA[timeId])):
				if(parentA[timeId][j][1] == i):
					timeOfParentA = timeId
					keyOfParentA = j
					break
			if(timeOfParentA != -1):
				break

		for timeId in parentB:
			for j in range(len(parentB[timeId])):
				if(parentB[timeId][j][1] == i):
					timeOfParentB = timeId
					keyOfParentB = j
					break
			if(timeOfParentB != -1):
				break

		# print (str(parentA) + ' '  + str(parentB))
		if(timeOfParentB != -1 and timeOfParentA != -1):
			temp = parentA[timeOfParentB][keyOfParentB][1]
			parentA[timeOfParentB][keyOfParentB][1] = parentA[timeOfParentA][keyOfParentA][1]
			parentA[timeOfParentA][keyOfParentA][1] = temp

			temp = parentB[timeOfParentB][keyOfParentB][1]
			parentB[timeOfParentB][keyOfParentB][1] = parentB[timeOfParentA][keyOfParentA][1]
			parentB[timeOfParentA][keyOfParentA][1] = temp

	# print (str(parentA) + ' ' + str(parentB))
	return (parentA,parentB)

def listOfcommulativeFitness(fitness):
	cummulativeFitness = []
	for i in range(len(fitness)+1):
		if(i == 0):
			cummulativeFitness.append(0)
		else:
			cummulativeFitness.append(cummulativeFitness[i-1] + fitness[i-1])
	return cummulativeFitness

def selection(cummulativeFitness):
	totalScore = cummulativeFitness[len(cummulativeFitness)-1] # total score of fitness function
	# print (totalScore)
	if(totalScore == 1):
		return "Cannot find a solution"
	randomSelection = random.randrange(0,totalScore-1,1)
	index = bisect.bisect_left(cummulativeFitness,randomSelection)
	index -= 1
	# print ('inside Selection :' + str(index))
	return index


def nextGeneration(populatio,fitness):
	cummulativeFitness = listOfcommulativeFitness(fitness)
	# half of population because each time 2 children will be generated with two parents
	# print (cummulativeFitness)
	nextGenerationPopulation = []
	while len(nextGenerationPopulation) < POPULATION_SIZE:
		parentAIndex = selection(cummulativeFitness) # getting index for parentA
		parentBIndex = selection(cummulativeFitness) # getting index for parentB
		# print ('index of next generation selection' + str(parentAIndex) + ' ' + str(parentBIndex))
		parentA = population[parentAIndex]
		parentB = population[parentBIndex]
		children = crossover(parentA,parentB) 
		# print(children)
		childrenA = mutation(children[0])
		childrenB = mutation(children[1])
		
		# delete this once mutation functions is uncommented
		# childrenA = children[0]
		# childrenB = children[1]

		nextGenerationPopulation.append(childrenA)
		nextGenerationPopulation.append(childrenB)

	# print (' ***** ')
	return nextGenerationPopulation

# Finding index with max possible fitness value of a generation
def findBestFitness(fitness):
	maxScore = -1
	maxIndex = -1
	for i in range(len(fitness)):
		if(fitness[i] > maxScore):
			maxScore = fitness[i]
			maxIndex = i
	return (maxScore,maxIndex)

''' 
	For the purpose of mutation for a chromosome.
	randomly select the amount of not assigned HR slot with assigned HR slot to be swapped within the range of max(1,CHROMOSOME_SIZE*RATE)
	
	swaping them afterwards
'''
def mutation(chromosome):
	rateRange = max(1,int(CHROMOSOME_SIZE*RATE))
	rate = random.randrange(0,rateRange,1)
	swappedListOfCandidates = {}
	for i in range(rateRange):
		toBeSwapedKey = -1
		toBeSwapedj = -1

		valueKey = -1
		valuej = -1 

		for key in chromosome:
			for j in range(len(chromosome[key])):
				if(chromosome[key][j][1] == -1):
					toBeSwapedKey = key
					toBeSwapedj = j
				else:
					if(chromosome[key][j][1] in swappedListOfCandidates):
						pass
					else:
						valueKey = key
						valuej = j

			if(toBeSwapedKey != -1 and valueKey != -1):
				swappedListOfCandidates[chromosome[valueKey][valuej][1]] = 1
				chromosome[toBeSwapedKey][toBeSwapedj][1] = chromosome[valueKey][valuej][1]
				chromosome[valueKey][valuej][1] = -1
				break
	return chromosome

''' 
	Taking input from console
'''
def main():	
	# For using global vairable inside function
	global CANDIDATE
	global CANDIDATE_COUNT
	global HR
	global HR_COUNT
	global DAYS
	global RATE
	global TOTAL_SLOTS_OF_HR

	print ('Enter the number of candidates : ',end='')
	CANDIDATE_COUNT = int(input())

	print ('Enter the Candidates priorites: (M/A/E/N)')
	for i in range(CANDIDATE_COUNT):
		availableTime = []
		a1 ,a2 ,a3, a4 = map(str,input().split())
		# for j in range(4):
		# 	a = input()
		# 	availableTime.append(a)
		availableTime.append(a1)
		availableTime.append(a2)
		availableTime.append(a3)
		availableTime.append(a4)
		CANDIDATE[i] = availableTime

	print ('Enter the number of HR: ',end='')
	HR_COUNT = int(input())
	print (HR_COUNT)
	for i in range(HR_COUNT):
		arr = []
		arr = list(map(int,input().split()))
		# print (arr)
		count = 1
		timeShiftAvailable = []
		TOTAL_SLOTS_OF_HR += 1
		for j in range(arr[0]):
			timeAvailable = []
			timeAvailable.append(arr[count])
			timeAvailable.append(arr[count+1])
			count+=2;
			TOTAL_SLOTS_OF_HR += 1
			timeShiftAvailable.append(timeAvailable)

		# timeShiftAvailable = []
		# for j in range(shifts):
		# 	day = int(input())
		# 	timeShiftFrom = int(input())
		# 	
		HR[i] = timeShiftAvailable

	DAYS = int(input())
	
	# print
	print('')
	print('candidate :' + str(CANDIDATE))
	print('HR :' + str(HR))
	print('Days :' + str(DAYS))

# if ( __name__ == "main"):

main() # to take the input
population = generatePopulation() # to generate Population
# print(population)

all_time_best_fitness = -1
all_time_best_chromosome = []
# Generating population and finding the max possible fitness value in a generation
for i in range(NO_OF_ITERATION):
	fitnessScore = fitnessAll(population) # Calculate fitness of population
	# print (fitnessScore)
	# print (population)
	bestFitness = findBestFitness(fitnessScore)
	if(all_time_best_fitness < bestFitness[0]):
		all_time_best_fitness = bestFitness[0]
		all_time_best_chromosome = population[bestFitness[1]]
	print ('Generation : ' + str(i) + ' Best fitness value'  + str(bestFitness) + ' Chromosome :' + str(population[bestFitness[1]]))
	population = nextGeneration(population,fitnessScore)

print ('All time best fitness over the generation : ' + str(all_time_best_fitness))
print ('All time best chromosome : ' + str(all_time_best_chromosome))

day = {}
for key in all_time_best_chromosome:
	c = []
	day[key//13] = c

for key in all_time_best_chromosome:
	for keyInChromosome in range(len(all_time_best_chromosome[key])):
		c = day[key//13]
		c.append((key%13,all_time_best_chromosome[key][keyInChromosome]))
		day[key//13] = c

print (day)
