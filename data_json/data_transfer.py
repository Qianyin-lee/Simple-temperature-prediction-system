import socket
import json
from data_json.weather_report import Report
import datetime


class sendData:

    # host = '192.168.137.80'
    # host = '192.168.43.161'
    # host = '127.0.0.1'
    port = 6000
    ser = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # hostname = socket.gethostname()
    # host = socket.gethostbyname(hostname)

    # host = socket.gethostname()
    # print(host)
    ser.bind(('', port))
    ser.listen(1)
    r = Report()
    print('服务器启动成功！')

    def send(self):
        while True:
            con, address = self.ser.accept()
            print('客户端连接成功！')
            while True:
                try:
                    rec_data = con.recv(1024)
                    print(rec_data)
                    data = json.loads(rec_data)['date']
                    number = json.loads(rec_data)['number']
                    print('number:', number)
                    date = datetime.datetime.strptime(data, "%Y-%m-%d")
                    # print(date)
                    year = date.year
                    month = date.month
                    day = date.day
                    # print(year, month, day)
                    str_data = self.r.temp_report(int(year), int(month), int(day), int(number))
                    print(str_data)
                    con.send(str_data.encode())
                    print('发送成功！')
                    # print(r.temp_report(year=year, month=month, day=day))
                except Exception as e:
                    print(e)
                    break
                finally:
                    con.close()


s = sendData()
s.send()
