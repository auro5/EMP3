import cv2
import glob
import random
import sys
import os
import numpy as np

emotions = ["anger", "happy", "sadness"] 
fishface = cv2.createFisherFaceRecognizer() 
data = {}

def run_recognizer():
    fishface.load("trained_emoclassifier.xml")
    prediction_data1 = []
    files = glob.glob("face_cut\\try\\*")
    prediction = files[:]
    img=cv2.imread(prediction[0])
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    prediction_data1.append(gray)
    pred1, conf1 = fishface.predict(prediction_data1[0])
    return pred1
	
var = sys.argv[1]
sys.argv = ['face.py',var]
execfile("face.py")
correct = run_recognizer()
print emotions[correct]