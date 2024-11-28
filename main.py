import shutil
import sqlite3
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager, UserMixin, login_user, logout_user

from flask import Flask, flash, render_template, request, send_file, redirect, url_for, make_response
from werkzeug.utils import secure_filename
import os
import io

from Compiler.compiler import compile

# Flask app reference
app = Flask(__name__)

# Database configuration
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///db.sqlite"
app.config["SECRET_KEY"] = "abc"
db = SQLAlchemy()

# File upload configuration
app.config['UPLOAD_FOLDER'] = 'uploads'
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024  # 16MB max file size
ALLOWED_EXTENSIONS = {'md'}

# Ensure upload directory exists
os.makedirs(app.config['UPLOAD_FOLDER'], exist_ok=True)

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

# Users entity/object
class Users(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(250), unique=True, nullable=False)
    password = db.Column(db.String(250), nullable=False)

db.init_app(app)
with app.app_context():
    db.create_all()

# Init login manager
login_manager = LoginManager()
login_manager.init_app(app)

# Login manager user loader function
@login_manager.user_loader
def load_user(user_id):
    return Users.query.get(user_id)

# Register route
@app.route('/register', methods=["GET", "POST"])
def register():
    if request.method == "POST":
        # Input validation
        if request.form.get("username") == "" or request.form.get("password") == "" or len(request.form.get("username")) > 250 or len(request.form.get("password")) > 250 or " " in request.form.get("username") or " " in request.form.get("password") or not request.form.get("username").isprintable() or not request.form.get("password").isprintable():
            return render_template("signup.html", authenticationFailedReason="InputValidation")

        user = Users(username=request.form.get("username"), password=request.form.get("password"))

        try:
            db.session.add(user)
            db.session.commit()

            login_user(user)

            return redirect(url_for("home"))
        except sqlite3.IntegrityError: # Username is already taken (not unique)
            return render_template("signup.html", authenticationFailedReason="sqlite3.IntegrityError")

    return render_template("signup.html", authenticationFailedReason="General")

# Login route
@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        user = Users.query.filter_by(username=request.form.get("username")).first()

        if user == None:
            return render_template("login.html", authenticationFailedReason="UserNotFound")

        if user.password == request.form.get("password"):
            login_user(user)
            return redirect(url_for("home"))
    
    return render_template("login.html", authenticationFailedReason="General")

# Logout route
@app.route("/logout")
def logout():
    logout_user()
    return redirect(url_for("login"))

# Home page route
@app.route("/", methods=['GET', 'POST'])
def home():
    return render_template("home.html")

# Upload route
@app.route('/upload', methods=['GET', 'POST'])
def upload():
    print("hello")
    if request.method == "POST":
        # Check if a file was uploaded
        if 'file' not in request.files:
            print('NoFileUpload', request.files)
            flash('NoFileUpload')
            return redirect(url_for('home'))
        
        file = request.files['file']
        
        # If no file selected
        if file.filename == '':
            print('NoFileSelection')
            flash("NoFileSelection")
            return redirect(url_for('home'))
        
        # If file is valid and allowed
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
            file.save(filepath)
            
            # Read file content
            try:
                # with open(filepath, 'r', encoding='utf-8') as f:
                    # content = f.read()
                compile(False, "#000000", "#ffffff", filepath)
                shutil.move("generated_pdf_from_md.pdf", "static/output/generated_pdf_from_md.pdf")
                shutil.move("temp.css", "static/output/temp.css")
                print('Generated')
                flash("generated_pdf_from_md.pdf")
                return redirect(url_for("home"))
            except Exception as e:
                print(str(e))
                flash("Error reading file")
                return redirect(url_for('home'))
                # "Error reading file: {str(e)}"
        
    print('General error')
    flash("General")
    return redirect(url_for('home'))
    # General error

@app.route('/download', methods=['GET', 'POST'])
def download_pdf():
    try:
        return send_file(
            'static/output/generated_pdf_from_md.pdf',
            as_attachment=True,
            download_name='generated_pdf_from_md.pdf'
        )
        # flash("Download successful")
        # return redirect(url_for('home'))
    except Exception as e:
        flash("Error downloading file")
        print(str(e))
        return redirect(url_for('home'))
        # return f"Error downloading file: {str(e)}"

if __name__ == "__main__":
    app.run(debug=True) # Flask debug mode ON
