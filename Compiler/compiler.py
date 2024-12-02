import markdown
import pdfkit
import re

def getPDF(input, css=''):
  html = markdown.markdown(input)
  if(css != ''):
    pdfkit.from_string(html, "generated_pdf_from_md.pdf", verbose=True, css=css, options={
      'page-size': 'Letter',
      'margin-top': '1.0in',
      'margin-right': '1.0in',
      'margin-bottom': '1.0in',
      'margin-left': '1.0in',
      'encoding': "UTF-8",
    })
  return open("generated_pdf_from_md.pdf")

# serif, txtcolor, bgcolor, filePath = getFromWebsite()

def compile(serif, txtcolor, bgcolor, filePath):
  check = re.compile("#\\d{6}")
  if not re.match(check, txtcolor) or not re.match(check, bgcolor):
    return "temp.pdf"
  if serif != False and serif != True:
    return "temp.pdf"
  with open(filePath) as file:
    with open("temp.css", "w") as css:
      css.write("""p, h1, h2, h3, h4, h5, h6 {
          font-family: """ + ('sans-serif' if not serif else 'serif') + """;
          color: """ + txtcolor + """;
        }

        body {
          background-color: """ + bgcolor + """;
        }""")

    getPDF("\n".join(file.readlines()), "temp.css")
    return "generated_pdf_from_md.pdf"

"""
Test Cases:
serif = False
serif = True
"""
