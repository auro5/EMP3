import cv2
import glob
from PIL import Image
faceDet = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")


def detect_faces(emotion):
    files = glob.glob("face_uncut\\%s\\123.jpg" %emotion) 

    filenumber = 0
    for f in files:
        frame = cv2.imread(f) 
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) 
        

        face = faceDet.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=10, minSize=(5, 5), flags=cv2.CASCADE_SCALE_IMAGE)
        
        if len(face) == 1:
            facefeatures = face
        else:
            facefeatures = ""
        

        for (x, y, w, h) in facefeatures: 
            gray = gray[y:y+h, x:x+w] 
            
            try:
                out = cv2.resize(gray, (350, 350)) 
                cv2.imwrite("face_cut\\%s\\%s.jpg" %(emotion, filenumber), out) 
            except:
               pass

emotion= "try"
argument = sys.argv[1]
im1 = Image.open("uploads\\%s.png" %argument)
im1.save("face_uncut\\try\\123.jpg",optimize=True,quality=95)
detect_faces(emotion) 
