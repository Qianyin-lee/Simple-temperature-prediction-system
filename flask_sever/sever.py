# encoding: utf-8

import json
import sys
import os
from flask_sockets import Sockets
import time

import socket
from gevent import monkey
from flask import *
from gevent import pywsgi
from geventwebsocket.handler import WebSocketHandler
from ftfy import fix_text

'''
import multiprocessing
import uuid
from threadpool import ThreadPool, makeRequests
'''

sys.path.append(os.path.abspath(os.path.dirname(__file__) + '/' + '..'))
sys.path.append("..")
monkey.patch_all()
app = Flask(__name__)
sockets = Sockets(app)
now = time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime(time.time()))

#与web进行通信
@sockets.route('/test')  # 指定路由
def echo_socket(ws):
    while not ws.closed:
        ws.send(str("message test!"))  # 回传给clicent
        """ 服务端必须接收到客户端发的消息才能保持该服务运行，如果ws.receive()没有接收到客户端发送的
         消息，那么它会关闭与客户端建立的链接
        """
        message = ws.receive()  # 接收到消息
        if message is not None:
            print('web client connected')
            print(message)
            print(type(message))
            message = json.loads(message)
            print(message['options'])
            """ 如果客户端未发送消息给服务端，就调用接收消息方法，则会导致receive()接收消息为空，关闭此次连接 """
            temp=b''

            error = None
            s = socket.socket()
            host = '192.168.137.228'
            port = 5500
            s.connect((host, port))
            print(host)
            s.send(json.dumps(message).encode('UTF8'))
            s.shutdown(socket.SHUT_WR)
            info=s.recv(1024)
            while info:
                temp+=info
                info=s.recv(1024)
                if(info==b''):
                    break
            print('Server Closed')

            if message['options']=='3':
                data=json.loads(temp)
                day=[]
                max=[]
                min=[]
                num=int(message['number'])
                i=0
                while i<num:
                    day.append(data[i]['date'])
                    temp=str((round((float(data[i]['tmax'])-32)/1.8,2)))
                    max.append(temp)
                    temp = str((round((float(data[i]['tmin']) - 32) / 1.8, 2)))
                    min.append(temp)
                    i=i+1
                r={'day':day,'max':max,'min':min}
                print(r)
            else:
                r=temp.decode()
                print(r)
                r=r.replace(":",": ")
                r=r.replace("/","")
                r=r=fix_text(r)

                r =json.loads(temp,strict=False)


            result=json.dumps(r)


            ws.send(result)



            print(now, "no receive")



#与微信小程序进行通信
@app.route('/api/weather', methods=['GET','POST']) # 指定路由
def api_weather():
    print('wechat connected')
    date1=request.json['pick']
    date2= date1.split('-', 1)
    print(date2[0])
    global r
    if int(date2[0])>2012:
        r={'options': '4', 'date':date1,'number':'100'}
    else:
        r={'options': '3','date':date1,'number':'100'}
    temp = b''
    error = None
    s = socket.socket()
    host = '192.168.137.228'
    port = 5500
    s.connect((host, port))
    print(host)
    s.send(json.dumps(r).encode('UTF8'))
    print(r)
    s.shutdown(socket.SHUT_WR)
    info = s.recv(1024)
    while info:
        temp += info
        info = s.recv(1024)
        if (info == b''):
            break

    if r['options'] == '3':
        data = json.loads(temp)
        day = []
        max = []
        min = []
        num = int(r['number'])
        i = 0
        while i < num:
            temp=data[i]['date'].split('-',2)
            temp=temp[0]+temp[1]
            day.append(data[i]['date'])
            temp = str((round((float(data[i]['tmax']) - 32) / 1.8, 2)))
            max.append(temp)
            temp = str((round((float(data[i]['tmin']) - 32) / 1.8, 2)))
            min.append(temp)
            i = i + 1

        r = {'day': day, 'max': max, 'min': min}
        print(r)
    else:
        r = temp.decode()
        r=json.loads(fix_text(r))
        print(r['day'])
    return (jsonify(r))
@app.route('/')
def hello():
        error=None
        if request.method=="GET": #如果请求为GET打开login.htmlreturn 'Hello World! server start！'
            return render_template('index.html')


if __name__ == "__main__":
    server = pywsgi.WSGIServer(('0.0.0.0', 5000), app, handler_class=WebSocketHandler)
    print('server start')
    server.serve_forever()
