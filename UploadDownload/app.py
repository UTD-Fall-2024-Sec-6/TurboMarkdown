from flask import Flask, render_template, request, send_file, redirect, url_for, make_response
from werkzeug.utils import secure_filename
import os
import io

app = Flask(__name__)

# Configuration
app.config['UPLOAD_FOLDER'] = 'uploads'
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024  # 16MB max file size
ALLOWED_EXTENSIONS = {'xml'}

# Ensure upload directory exists
os.makedirs(app.config['UPLOAD_FOLDER'], exist_ok=True)

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/upload', methods=['POST'])
def upload_file():
    # Check if a file was uploaded
    if 'file' not in request.files:
        return redirect(url_for('index'))
    
    file = request.files['file']
    
    # If no file selected
    if file.filename == '':
        return redirect(url_for('index'))
    
    # If file is valid and allowed
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        file.save(filepath)
        
        # Read file content
        try:
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            return render_template('editor.html', content=content, filename=filename)
        except Exception as e:
            return f"Error reading file: {str(e)}"
    
    return 'Invalid file type. Please upload an XML file.'

@app.route('/download_text', methods=['POST'])
def download_text():
    try:
        # Get the content from the form
        content = request.form.get('content', '')
        
        # Create text file in memory
        text_file = io.BytesIO()
        text_file.write(content.encode('utf-8'))
        text_file.seek(0)
        
        return send_file(
            text_file,
            mimetype='text/plain',
            as_attachment=True,
            download_name='edited_content.txt'
        )
    except Exception as e:
        return f"Error downloading file: {str(e)}"

if __name__ == '__main__':
    app.run(debug=True)