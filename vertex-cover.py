fread = open("graph-4.txt", "r")
input=fread.read();
graph_dict={};    # value of the key - vertex are the list of edges
vertexcover=[];
def init_graph():
    global graph_dict;
    list=input.rstrip('x').split('x');
    for item in list:
        list_= item.split(':');
        graph_dict[list_[0]]=list_[1].split(','); #initializing graph

def writesetstofile(set):
    name="output.txt";    
    with open(name, 'w') as f:
        for item in set:
            f.write("{}x".format(item))
            
def removefrom_dict(item1,item2):
    global graph_dict;
    if graph_dict.has_key(item1):
        if(item2 in graph_dict[item1]):    
            graph_dict[item1].remove(item2);
            print( "removed : ",item2);
def find_max_edge_count():
    global vmax;
    maxedgecount=0;
    vmax="";
    for key,edgelist in graph_dict.iteritems():        
        if len(edgelist)>maxedgecount:
            maxedgecount=len(edgelist);
            vmax=key;
    return vmax;

curIndex=0;
init_graph();
templist=[]
while(curIndex<len(graph_dict)):
        vmax=find_max_edge_count();
        if vmax=='':
            break;
        print ("vmax:",vmax);
        edgelist=graph_dict[vmax];
        for edge in edgelist:
            templist=graph_dict[edge];

            if vmax in templist:        
                templist.remove(vmax);
            graph_dict[edge]=templist[:];
        vertexcover.append(vmax);
        graph_dict[vmax]=[];
        curIndex+=1;
        
writesetstofile(vertexcover);
print ("vertex cover:",vertexcover)
print ("vertex cover length:",len(vertexcover))
print("count of vmax",len(vertexcover));
