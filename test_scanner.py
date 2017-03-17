#!/usr/bin/python3.6
import glob
import subprocess
import os

if not os.path.exists('./solutions'):
        os.makedirs('./solutions')

filenames = []
for f in glob.glob('./Scanner/*.vc'):
    completed_process = subprocess.run(['java', 'VC.vc', f], stdout=subprocess.PIPE)
    output = completed_process.stdout
    filename = './solutions/' + f.rsplit('/', maxsplit=1)[1]
    temp = f.rsplit('/', maxsplit=1)[1]
    temp = temp.split('.')[0]
    filenames.append(temp)
    sol_f = open(filename, 'w')
    sol_f.write(output.decode('utf-8'))
    sol_f.close()

print(filenames)
my_files = glob.glob('./solutions/*.vc')
diff_file = open('./diff.txt', 'w')
for f in filenames:
    mine = './solutions/' + f + '.vc'
    sol = './Scanner/' + f + '.sol'
    diff_file.write("testing " + mine + "  and  " + sol ) 
    diff_file.write("--------------------------------------------")
    cp = subprocess.run(['diff', mine, sol], stdout=subprocess.PIPE)
    output = cp.stdout
    diff_file.write(output.decode('utf-8'))
    diff_file.write("--------------------------------------------\n\n")
    #print("cp output = " + " " + output.decode('utf-8'))

diff_file.close()
