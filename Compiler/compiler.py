import markdown
import pdfkit
import re
import unittest

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

def compile(serif, txtcolor, bgcolor, filePath):
  check = r"#(\d|[a-f]){6}"
  if not (bool(re.fullmatch(check, txtcolor)) and bool(re.fullmatch(check, bgcolor))):
    return "a.pdf"

  if (type(serif) != bool):
    return "b.pdf"

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

