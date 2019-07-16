from mod_timeseries import weather_training
import datetime
import json


class Report:
    _p_q_min = (0, 0)
    _p_q_max = (0, 0)

    def __init__(self):
        p_q = weather_training.find_p_q('0710.csv')#确定合适的p,q值
        self._p_q_min = p_q[0]
        self._p_q_max = p_q[1]
        print(self._p_q_min, self._p_q_max)

    def temp_report(self, year: int, month: int, day: int, number: int):
        # localtime = time.localtime(time.time())
        # year = localtime.tm_year   #年份
        # month = localtime.tm_mon   #月份
        # day = localtime.tm_mday   #日期
        # month = 1
        # day = 1
        # print(month,day)

        # print(year, month, day)
        # s = date_format(year, month, day)  #获得标准化的日期
        # print(s)
        # date = s[0] + s[1] + '.csv'   #获得文件名
        # data_today = weather_training.mod_temp(date, self._p_q_min, self._p_q_max)   #预测数据
        # print(data_today)

        d = []
        min = []
        max = []
        dist = {}   #新建字典
        date = str(year) + '-' + str(month) + '-' + str(day)
        d1 = datetime.datetime.strptime(date, '%Y-%m-%d')
        # print(d1)
        for i in range(number):
            day_i = i+1
            delt = datetime.timedelta(days=day_i)
            n = d1 + delt
            # s_i = date_format(year, month, day_i)
            # print(s_i)
            # date_i = s_i[0] + s_i[1] + '.csv'
            # days = s_i[2] - 2012
            if n.month < 10:
                s_1 = str(0) + str(n.month)
            else:
                s_1 = str(n.month)

            if n.day < 10:
                s_2 = str(0) + str(n.day)
            else:
                s_2 = str(n.day)
            # print(" ss", s_1, s_2)
            date_i = s_1 + s_2 + '.csv'
            days = n.year - 2012

            if days <= 0:
                print("日期错误！")
                return

            data_i = weather_training.mod_temp(date_i, self._p_q_min, self._p_q_max, days)
            d.append(s_1 + s_2)
            max.append(data_i[0])
            min.append(data_i[1])
            # dist[] = data_i

        dist['day'] = d
        dist['min'] = min
        dist['max'] = max
        data_json = json.dumps(dist)

        # print(type(data_json))
        return data_json

    # def temp_find(self, year, month, day):
    #     print("查询")
    #     s = date_format(year, month, day)
    #     print(s)
        # date = s[0] + s[1] + '.csv'
        # data_today = weather_training.mod_temp(date, self._p_q_min, self._p_q_max)
        #
        # dist = {}
        # dist[s[0] + s[1]] = data_today
        # print(data_today
        # data_json = json.dumps(dist)
        # return data_json


def isleap(year):
    if (year % 4) == 0:
        if (year % 100) == 0:
            if (year % 400) == 0:
                return True # 整百年能被400整除的是闰年
            else:
                return False
        else:
            return True
    else:
        return False


def date_format(year, month, day):
    month_30 = [4, 6, 9, 11]
    month_31 = [1, 3, 5, 7, 8, 10, 12]
    if month == 2:
        if isleap(year):
            if day > 29:
                month = month + 1
                day = day - 29
        else:
            if day > 28:
                month = month + 1
                day = day - 28

    if month in month_30:
        if day > 30:
            month = month + 1
            day = day - 30

    if month in month_31:
        if day > 31:
            month = month + 1
            day = day - 31

    if month > 12:
        year = year + 1
        month = month - 12

    if month < 10:
        s_1 = str(0) + str(month)
    else:
        s_1 = str(month)

    if day < 10:
        s_2 = str(0) + str(day)
    else:
        s_2 = str(day)

    return s_1, s_2, year


# r = Report()
# print(r.temp_report(2019, 5, 6))
# print(r.temp_report())
# print(r.temp_find(2019, 12, 2))
