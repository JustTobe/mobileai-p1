from requests import get
from bs4 import BeautifulSoup

url = 'http://capec.mitre.org/data/definitions/1000.html'
r = get(url)
soup = BeautifulSoup(r.text, 'html.parser')

tabeldetail = soup.find_all('div', class_ = 'tabledetail')[1]
indent = tabeldetail.find('div', class_ = 'indent')
x = [i for i in indent.children][6]

indent = -1
def outputTree(nodes):
    global indent
    indent += 1
    for item in nodes:
        print('\t'*indent + item['name'] + item['id'] + ':' + item['type'])
        outputTree(item['children'])
    indent -= 1

def makeCyper():
    def output(node):
        for item in node['children']:
            f.write('create (:%s{name: "%s", id: %s});\n' % (item['type'], item['name'], item['id']))
            f.write('match (p:%s{id: %s}), (c:%s{id: %s}) create (p)-[:ParentOf]->(c);\n' % (node['type'], node['id'], item['type'], item['id']))
            output(item)
    with open('/home/luncert/Desktop/cyper.data', 'wb') as f:
        for item in tree:
            f.write('create (:%s{name: "%s", id: %s});\n' % (item['type'], item['name'], item['id']))
            output(item)

def dbAccess():
    from py2neo import Graph, Node, Relationship
    def output(node, p_node):
        for item in node['children']:
            c_node = Node(name = item['name'], id = item['id'], eventType = item['type'])
            c_node.add_label('Event')
            graph.create(c_node)
            # (child)-[r:Derivation]->(parent)
            graph.create(Relationship(c_node, 'Derivation', p_node))
            output(item, c_node)
    graph = Graph("http://luncert.cn:7474",username = "neo4j",password = "Luncert428")
    for item in tree:
        p_node = Node(name = item['name'], id = item['id'], sceneType = item['type'])
        p_node.add_label('Scene')
        graph.create(p_node)
        output(item, p_node)

def handleId(cid):
    if cid.startswith('('):
        cid = cid[1:]
    if cid.endswith(')'):
        cid = cid[:-1]
    return cid

def tranverse(target, base):
    for item in target.children:
        span = item.findChild('span')
        if span.attrs.get('id') != None:
            # not leaf node
            # if span and span.next_sibling and span.next_sibling.next_sibling:
            type = span.next_sibling.span.img.attrs.get('alt').replace(' ', '')
            span = span.next_sibling.next_sibling
            node = dict(name=span.a.getText(), id=handleId(span.span.i.getText()), children=[], type=type)
            base['children'].append(node)
            if item.div and item.div.span: tranverse(item.div.span, node)
        else:
            # be leaf node
            type = span.span.img.attrs.get('alt').replace(' ', '')
            span = span.next_sibling
            node = dict(name=span.a.getText(), id=handleId(span.span.i.getText()), children=[], type=type)
            base['children'].append(node)

tree = []
beFirst = True
for item in x.children:
    if not beFirst:
        span = item.findChild('span').next_sibling.next_sibling
        name = span.a.getText()
        cid = handleId(span.span.i.getText())
        node = dict(name=name, id=cid, children=[], type='Category')
        tree.append(node)
        tranverse(item.div.span, node)
    else:beFirst = False

# outputTree(tree)
dbAccess()
