import _pickle as cp
import numpy as np
import matplotlib.pyplot as plt

X, y = cp.load(open('winequality-white.pickle', 'rb'))

# 80% training data, 20% test data

N, D = X.shape
N_train = int(0.8 * N)
N_test = N - N_train

X_train = X[:N_train]
y_train = y[:N_train]
X_test = X[N_train:]
y_test = y[N_train:]

#  ------
# | Ex 1 |
#  ------
count_y_train = [0, 0, 0, 0, 0, 0, 0]
for x in y_train:
    count_y_train[int(x-3)] += 1

plt.xticks(range(len(count_y_train)), range(3, 10))
plt.xlabel('y-value')
plt.ylabel('Count')
plt.title('Training Set y-value Distribution')
plt.bar(range(len(count_y_train)), count_y_train)

print('Hand-in 1: Bar chart showing distribution of y values in the training set. \nClose plot pane to continue.\n ')
plt.show()

#  ------
# | Ex 2 |
#  ------

# Lines 39-45 manually work out our avg value of y for the predictor using Ex1 y-counts
# because the manual avg has faster computation time than calculating np.mean
# post: avg_y_train == np.mean(y_train)
ls = []
i = 3  # because y values start from 3, but i could initiate at <3 for greater generality
for x in count_y_train:
    ls.append(x*i)
    i += 1
avg_y_train = np.sum(ls)/np.sum(count_y_train)
# Dev-only equality check: print('mean value: ' + str(np.mean(y_train)) + ' = manual value:' + str(avg_y_train))

y_train_pred = [avg_y_train] * N_train
y_test_pred = [avg_y_train] * N_test

mse_train_ex2 = np.square(np.subtract(y_train, y_train_pred)).mean()
mse_test_ex2 = np.square(np.subtract(y_test, y_test_pred)).mean()

print('Hand-in 2: Mean Squared Error (MSE) Report for simple predictor (average of training y-values)')
print('Simple Predictor MSE for training data: ', mse_train_ex2)
print('Simple Predictor MSE for test data: ', mse_test_ex2, '\n ')

#  ------
# | Ex 3 |
#  ------

# Standardise training data. Key: std = standard deviation, norm = standardised
X_tr_mean = X_train.mean(axis=0)
X_tr_std = X_train.std(axis=0)
X_tr_norm = (X_train - X_tr_mean) / X_tr_std

# Apply same transformation to test data
X_te_norm = (X_test - X_tr_mean) / X_tr_std

# Prepend ones column to standardised X datasets
X_tr_ex3 = np.hstack((np.ones((N_train, 1)), X_tr_norm))
X_te_ex3 = np.hstack((np.ones((N_test, 1)), X_te_norm))

# Training predictions given by y_tr_hat == ŷ
# where ŷ = Xw = X * (X^T * X)^-1 * X^T * y (Lecture 3, p.5)

w = np.matmul(np.linalg.inv(np.matmul(np.transpose(X_tr_ex3), X_tr_ex3)),
              np.dot(np.transpose(X_tr_ex3), y_train))
y_tr_hat = np.matmul(X_tr_ex3, w)

# Test predictions using learned weights
y_te_hat = np.matmul(X_te_ex3, w)

# Calculate MSEs
mse_train_ex3 = np.square(np.subtract(y_train, y_tr_hat)).mean()
mse_test_ex3 = np.square(np.subtract(y_test, y_te_hat)).mean()

print('Hand-in 3: MSE Report for closed form least squares estimate linear model')
print('Linear Model MSE for training data: ', mse_train_ex3)
print('Linear Model MSE for test data: ', mse_test_ex3, '\n ')

#  ------
# | Ex 4 |
#  ------


def mse(ys, y_hat):
    return np.square(np.subtract(ys, y_hat)).mean()


def calculate_train_error(x, y, n):
    X = np.hstack((np.ones((n, 1)), x[:n]))
    w = np.matmul(np.linalg.inv(np.matmul(np.transpose(X), X)),
              np.dot(np.transpose(X), y[:n]))
    return [mse(y[:n], np.dot(X, w)), w]


Ns = range(20, 620, 20)
train_errors = []
test_errors = []

for n in Ns:
    [train_error, w] = calculate_train_error(X, y, n)
    train_errors.append(train_error)
    X_te_ex4 = np.hstack((np.ones((N - n, 1)), X[n:]))
    y_te_hat = np.matmul(X_te_ex4, w)
    test_errors.append(mse(y[n:], y_te_hat))

print('Hand-in 4: Report of learning curves plot')
print('Linear model stops under-fitting after about 40 data-points are included.')
print('About 140 data-points in the training set is adequate for optimal test error.')


plt.plot(Ns, train_errors, label="training error")
plt.plot(Ns, test_errors, label="test error")
plt.xlabel('Size of the dataset used for training')
plt.ylabel('Error')
plt.title('Linear Model Learning Curves Plot')
plt.legend()
plt.show()

