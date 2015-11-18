import copy;
vcset=[]
kotilist=[]
kotilist1=[]
inputdict={}
visitedVertex={};
edgeCount={};
sortedkeys=[];
def writesetstofile(set):
    name="vc.txt";    
    with open(name, 'w') as f:
        for item in vcset:
            f.write("{}x".format(item))
  
def sortVertices():
    global sortedkeys,edgeCount;
    for key, value in reversed(sorted(edgeCount.iteritems(), key=lambda (k,v): (v,k))):
        sortedkeys.append(key);
def intersect(a, b):
     return list(set(a) & set(b))
        
def main():
    global kotilist1,kotilist,inputdict,visitedVertex,edgecount,bufferdict,sortedkeys,edgeCount;
    edgecount=0;    
    coveredcount=0;
    uncoveredcount=0;
    fread2 = open("output.txt", "r")  
    kotifile=fread2.read();
    kotilist=kotifile.rstrip('x').split('x');

    outputlen=len(kotilist);
    fread = open("graph-4.txt", "r")
    input=fread.read();
     
    list=input.rstrip('x').split('x');
    for item in list:
        list_= item.split(':');
        inputdict[list_[0]]=list_[1].split(','); #initializing graph
        edgecount+=len(list_[1].split(','));
        visitedVertex[list_[0]]=-1;               #initializing visited vertex
        edgeCount[list_[0]]=len(list_[1].split(','));
    inputlen=len(inputdict);
    edgecount/=2
    bufferdict = copy.deepcopy(inputdict)
    count=0; count1=0;
    for vkey in kotilist:
        for edge in inputdict[vkey]:
            if inputdict.has_key(vkey) and inputdict.has_key(edge):
                if edge in bufferdict[vkey]:
                    bufferdict[vkey].remove(edge);
                    count+=1;
                if vkey in bufferdict[edge]:
                    bufferdict[edge].remove(vkey)
                    count+=1;
                                
    coveredcount=0;
    uncoveredcount=0;
    for vkey,valuelist in bufferdict.items():
        if not valuelist:
            coveredcount+=1
        else:
            uncoveredcount+=1
    print bufferdict;        
    print("covered count: ",count)
    print("covered: ",coveredcount)
    print("uncovered: ",uncoveredcount)
        
if __name__ == "__main__":
    main();     
