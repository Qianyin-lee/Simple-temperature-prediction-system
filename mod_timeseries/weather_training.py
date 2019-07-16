import pandas as pd
import numpy as np
import warnings
from statsmodels.tsa.arima_model import ARIMA
from statsmodels.graphics.api import qqplot
import statsmodels.api as sm
import matplotlib.pyplot as plt


def mod_temp(name, p_q_min, p_q_max, days):
    data = data_init(name)
    dta_min = data[0]
    dta_max = data[1]

    # pd.Index(sm.tsa.datetools.dates_from_range(str(begin_year.values[0]), str(end_year.values[0])))
    # dta_min.plot(figsize=(10, 6))
    # diff1 = dta_min.diff(1)
    # print(diff1)
    # fig = plt.figure(figsize=(12, 8))
    # ax = fig.add_subplot(111)
    # ax.plot(diff1)
    # fig = plt.figure(figsize=(12, 8))
    # ax1 = fig.add_subplot(211)
    # fig = sm.graphics.tsa.plot_acf(dta_min, lags=30, ax=ax1)
    # ax2 = fig.add_subplot(212)
    # fig = sm.graphics.tsa.plot_pacf(dta_min, lags=30, ax=ax2)
    # plt.show()

    # 一阶差分
    # diff1_min = dta_min.diff(1).dropna()
    # diff1_max = dta_max.diff(1).dropna()
    # 建立ARIMA模型
    model_min = ARIMA(dta_min, (p_q_min[0], 1, p_q_min[1])).fit()
    model_max = ARIMA(dta_max, (p_q_max[0], 1, p_q_max[1])).fit()
    # print(model_min.forecast()[0])

    min_data = model_min.forecast(days)[0]
    max_data = model_max.forecast(days)[0]
    print(min_data)
    print(max_data)
    index = days-1
    min = min_data[index]
    print(min)
    max = max_data[index]
    print(max)


    # dta_min = dta_min.diff(1)
    # plt.plot(dta_min)
    # plt.plot(model_min.fittedvalues, color='red')
    # resid = model_min.resid
    # fig = plt.figure(figsize=(12, 8))
    # ax = fig.add_subplot(111)
    # fig = qqplot(resid, line='q', ax=ax, fit=True)
    # plt.show()
    # return min, max

    min = (min-32)/1.8
    max = (max-32)/1.8

    min_1 = '% .1f' % min
    max_1 = '% .1f' % max
    return max_1, min_1


def proper_model(data_ts):
    pmax = int(len(data_ts) / 10)  # 一般阶数不超过length/10
    qmax = int(len(data_ts) / 10)  # 一般阶数不超过length/10
    bic_matrix = []  # bic矩阵
    for p in range(pmax + 1):
        tmp = []
        for q in range(qmax + 1):
            try:  # 存在部分报错，所以用try来跳过报错。
                tmp.append(ARIMA(data_ts, (p, 1, q)).fit().bic)
            except:
                tmp.append(None)
        bic_matrix.append(tmp)
    # print(bic_matrix)
    bic_matrix = pd.DataFrame(bic_matrix)  # 从中可以找出最小值
    # print(bic_matrix)
    # print(bic_matrix.stack())
    p, q = bic_matrix.stack().astype('float64').idxmin()  # 先用stack展平，然后用idxmin找出最小值位置。
    # print(u'BIC最小的p值和q值为：%s、%s' % (p, q))
    return p, q


def data_init(name):
    data = pd.read_csv(name, parse_dates=['date'])
    dta_min = data['tmin']
    dta_max = data['tmax']
    dta_year = data['date']
    dta_min = np.array(dta_min, dtype=np.float)
    dta_max = np.array(dta_max, dtype=np.float)
    dta_min = pd.Series(dta_min)
    dta_max = pd.Series(dta_max)
    dta_min.index = dta_year
    dta_max.index = dta_year
    # print(dta_max)
    # print(dta_min)

    warnings.filterwarnings("ignore")
    return dta_min, dta_max


def find_p_q(name):
    dta = data_init(name)
    p_q_min = proper_model(dta[0])#min
    p_q_max = proper_model(dta[1])#max
    return p_q_min, p_q_max



