import numpy as np
import math
from sklearn.linear_model import LogisticRegression
from sklearn.datasets import load_iris
import matplotlib.pyplot as plt


class NBC:

    def __init__(self, feature_types, num_classes):
        self.feature_types: [str] = feature_types
        self.n_classes: int = num_classes
        self.classes_ = None
        self.is_fitted: bool = False
        self.class_prior_ = None
        self.mu_ = None
        self.sigma_ = None

    def fit(self, X, y):
        # estimate all the parameters of the NBC
        classes_ = self.classes_ = np.unique(y)
        n_c = self.n_classes
        assert classes_.size == n_c, \
            f'Number of classes in init arg, {n_c}, is not equal to no. unique labels in ytrain, {classes_.size}.'
        X_by_class = np.array([X[c == y] for c in classes_])
        self.class_prior_ = self.calc_priors(X_by_class, y)
        self.mu_ = self.calc_mus(X_by_class)
        self.sigma_ = self.calc_sigmas(X_by_class)
        self.is_fitted = True

    @staticmethod
    def calc_priors(X_by_class, y):
        return np.array([X_c.size / y.size for X_c in X_by_class])

    @staticmethod
    def calc_mus(X_by_class):
        # mean for each feature per class
        return np.array([X_c.mean(axis=0) for X_c in X_by_class])

    @staticmethod
    def calc_sigmas(X_by_class):
        # standard deviation for each feature per class
        # condition: var!=0  # thus sigma = max(sigma,1e-6)
        return np.array([np.maximum(X_c.std(axis=0), 1e-6) for X_c in X_by_class])

    def predict(self, X):
        # compute the class conditional probabilities for the new samples on all classes
        # returns the class with largest conditional probability for each sample
        assert self.is_fitted, 'Model must be fitted before prediction.'
        jll = self._joint_log_likelihood(X)
        return [self.classes_[i] for i in np.argmax(jll, axis=1)]

    def _joint_log_likelihood(self, X):
        N, D = X.shape
        jll = np.zeros(shape=(N, self.n_classes))
        log_class_prior_ = np.log(self.class_prior_)
        for i in range(0, N):
            jll[i] = log_class_prior_
            for c in range(0, self.n_classes):
                jll[i][c] += np.sum(self.log_pdfs(X[i, :], self.mu_[c], self.sigma_[c]))
        return jll

    @staticmethod
    def log_pdfs(x, mu, sigma):
        D = len(x)-1
        diff = [(x[i] - mu[i]) for i in range(D)]
        top = [diff[i]**2/(2*sigma[i]**2) for i in range(D)]
        bottom = sigma * math.sqrt(2 * math.pi)
        bottom = [math.log(bottom[i]) for i in range(D)]
        logPdf = [-(bottom[i] + top[i]) for i in range(D)]
        return logPdf


iris = load_iris()
X, y = iris['data'], iris['target']


def shuffle(X, y):
    N, D = X.shape
    Ntrain = int(0.8 * N)
    shuffler = np.random.permutation(N)
    Xtrain = X[shuffler[:Ntrain]]
    ytrain = y[shuffler[:Ntrain]]
    Xtest = X[shuffler[Ntrain:]]
    ytest = y[shuffler[Ntrain:]]
    return Ntrain, Xtrain, ytrain, Xtest, ytest


nbc = NBC(feature_types=['r', 'r', 'r', 'r'], num_classes=3)
Ntrain, Xtrain, ytrain, Xtest, ytest = shuffle(X, y)

nbc.fit(Xtrain, ytrain)
prediction = nbc.predict(Xtest)
test_accuracy = np.mean(prediction == ytest)
print("Test accuracy = " + str(test_accuracy))

# HANDIN 1
# In sklearn, C is the inverse regularisation strength. Thus, for lambda = 0.1, set C to 10.


def test_acc(pred, y): return np.mean(pred == y)


def compare_error():
    nbc_err = []
    lr_err = []
    lr = LogisticRegression(C=10, solver='liblinear')
    for i in np.arange(10):
        cum_nbc_err = 0
        cum_lr_err = 0
        percent = (i + 1) / 10
        for repeat in range(400):
            Ntrain, Xtrain, ytrain, Xtest, ytest = shuffle(X, y)
            n = int(percent * Ntrain)

            num_c = len(np.unique(ytrain[:n]))
            nbc = NBC(feature_types=['r', 'r', 'r', 'r'], num_classes=num_c)
            nbc.fit(Xtrain[:n], ytrain[:n])
            cum_nbc_err += 1 - test_acc(nbc.predict(Xtest), ytest)

            lr.fit(Xtrain[:n], ytrain[:n])
            cum_lr_err += 1 - test_acc(lr.predict(Xtest), ytest)
        nbc_err.append(cum_nbc_err / 400)
        lr_err.append(cum_lr_err / 400)

    return nbc_err, lr_err


nbc_err, lr_err = compare_error()

# HANDIN 2

plt.xlabel('Percentage of training data used')
plt.ylabel('Error')
plt.title('Comparison of mean NBC and LR classification error')
plt.xticks(list(range(10)), list(range(10, 110, 10)))
plt.plot(nbc_err, c='red', label='NBC Error')
plt.plot(lr_err, c='blue', label='LR Error')
plt.legend()

plt.show()
