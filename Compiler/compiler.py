import markdown
import pdfkit

def getPDF(input, css=''):
  html = markdown.markdown(input)
  if(css != ''):
    pdfkit.from_string(html, "temp.pdf", verbose=True, css=css, options={
      'page-size': 'Letter',
      'margin-top': '1.0in',
      'margin-right': '1.0in',
      'margin-bottom': '1.0in',
      'margin-left': '1.0in',
      'encoding': "UTF-8",
    })
  return open("temp.pdf")
  
serif = True
txtcolor = "#000"
bgcolor = "#fff"
filePath = "test.md"
  
# serif, txtcolor, bgcolor, filePath = getFromWebsite()

def compile(serif, txtcolor, bgcolor, filePath):
  with open(filePath) as file:  
    with open("temp.css", "w") as css:
      css.write("""p, h1, h2, h3, h4, h5, h6 {
<<<<<<< Updated upstream
          font-family:""" + 'sans-serif' if not serif else 'serif' + """;
          color:""" + txtcolor + """
=======
          font-family: """ + ('sans-serif' if not serif else 'serif') + """;
          color: """ + txtcolor + """;
>>>>>>> Stashed changes
        }

        body {
          background-color: """ + bgcolor + """;
        }""")
    
    getPDF("\n".join(file.readlines()), "temp.css")
    return "temp.pdf"
  
compile(serif, txtcolor, bgcolor, filePath)
