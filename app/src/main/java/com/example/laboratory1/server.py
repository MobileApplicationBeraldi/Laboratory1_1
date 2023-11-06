from flask import Flask, jsonify, request
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)

data={'Big Mac':'Nice selection','My Humburger':'Fantastic','Try this':'Wow'}

class myHUB(Resource):
    def get(self):
        return jsonify(data)


    @app.route('/order', methods=['GET'])
    def get2():
        return str(request.args)


api.add_resource(myHUB, '/')

if __name__ == '__main__':
    print('starting myHUB api...waiting')
