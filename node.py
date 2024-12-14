from flask import Flask, jsonify
import threading
import requests
import time
import os
from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)

LOAD_BALANCER_URL = os.getenv('LOAD_BALANCER_URL', 'http://localhost:8000/register')
SERVER_IP = os.getenv('SERVER_IP', '127.0.0.1')
SERVER_PORT = os.getenv('SERVER_PORT', 5000)

headers = {
    'X-Forwarded-For': SERVER_IP,  
    'X-Forwarded-Port': SERVER_PORT
}

@app.route('/process', methods=['GET'])
def process_request():
    return jsonify({'message': 'Request processed by node'}), 200

def register_with_load_balancer():
    while True:
        try:
            response = requests.get(
                LOAD_BALANCER_URL,
                headers = headers
            )
            if response.status_code == 204:
                print(f"Registered with load balancer")
            else:
                print(f"Failed to register with load balancer")
        except requests.RequestException as e:
            print(f"Error connecting to load balancer: {e}")
        
        time.sleep(5)  

if __name__ == '__main__':
    threading.Thread(target = register_with_load_balancer, daemon = True).start()
    app.run(host = '0.0.0.0', port = SERVER_PORT)