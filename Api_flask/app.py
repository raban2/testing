from flask import Flask, request, jsonify

# importing libraries
import pickle
import numpy as np

# unpickling the model
model = pickle.load(open('model.pkl', 'rb'))

# creating the object of the flask
app = Flask(__name__)


# creating the routes
@app.route('/')
def home():
    return "Hello World"


@app.route('/predict', methods=['POST'])
def predict():
    N = request.form.get('N')
    P = request.form.get('P')
    K = request.form.get('K')
    temperature = request.form.get('temperature')
    humidity = request.form.get('humidity')
    ph = request.form.get('ph')
    rainfall = request.form.get('rainfall')

    # Explicitly converting the datatypes
    N = float(N)
    P = float(P)
    K = float(K)
    temperature = float(temperature)
    humidity = float(humidity)
    ph = float(ph)
    rainfall = float(rainfall)

    # Creating 2D array
    input_query = np.array([[N, P, K, temperature, humidity, ph, rainfall]])

    # predicting
    result = model.predict(input_query)[0]

    # return result
    return jsonify({'label': str(result)})


if __name__ == '__main__':
    app.run(debug=True)
