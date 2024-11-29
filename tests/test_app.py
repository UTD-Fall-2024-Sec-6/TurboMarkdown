# Import sys module for modifying Python's runtime environment
import sys
# Import os module for interacting with the operating system
import os

# Add the parent directory to sys.path
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# Import the Flask app instance from the main app file
from main import app 
# Import pytest for writing and running tests
import pytest

@pytest.fixture
def client():
    """A test client for the app."""
    with app.test_client() as client:
        yield client

def test_register1(client):
    """Test the register route with test case 1 (refer to docs)."""
    response = client.post('/register', data={
        "username": "1234567890",
        "password": "utdcomets1969",
    })
    assert response.status_code == 302 # HTTP 302 == redirection detected

def test_register2(client):
    """Test the register route with test case 2 (refer to docs)."""
    response = client.post('/register', data={
        "username": "1234567890",
        "password": "utd\tcomets1969",
    })
    assert b"Create an account" in response.data

def test_register3a(client):
    """Test the register route with test case 3a (refer to docs)."""
    response = client.post('/register', data={
        "username": "1234567890",
        "password": "utd comets1969",
    })
    assert b"Create an account" in response.data

def test_register3b(client):
    """Test the register route with test case 3b (refer to docs)."""
    response = client.post('/register', data={
        "username": "1234567890",
        "password": "",
    })
    assert b"Create an account" in response.data

def test_register4(client):
    """Test the register route with test case 4 (refer to docs)."""
    response = client.post('/register', data={
        "username": "12 34567890",
        "password": "utdcomets1969",
    })
    assert b"Create an account" in response.data

def test_register7(client):
    """Test the register route with test case 7 (refer to docs)."""
    response = client.post('/register', data={
        "username": "",
        "password": "utdcomets1969",
    })
    assert b"Create an account" in response.data

def test_login1(client):
    """Login with correct credentials."""
    response = client.post('/login', data={
        "username": "1234567890",
        "password": "utdcomets1969",
    })
    assert response.status_code == 302

def test_login2(client):
    """Try to login with a username that's not in the database."""
    response = client.post('/login', data={
        "username": "888",
        "password": "utdcomets1969",
    })
    assert b"Sign in to your account" in response.data

def test_login3(client):
    """Try to login with a password that doesn't match the one on record."""
    response = client.post('/login', data={
        "username": "1234567890",
        "password": "utdcomets1968",
    })
    assert b"Sign in to your account" in response.data

# CLEAR THE EXISTING DATABASE BEFORE RUNNING TEST CASES, should probably create a separate database for testing