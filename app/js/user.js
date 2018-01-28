function submitform(id)
{
  var formsCollection = document.getElementsByTagName("form");
  var match = "myForm" + id;
  for(var i=0;i<formsCollection.length;i++)
  {
    if(formsCollection[i].name == match){
     formsCollection[i].submit();
    }
  }
}