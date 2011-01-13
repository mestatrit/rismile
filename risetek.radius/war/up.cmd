for %%c in (./rismile/*.*) do tftp -i %1 PUT ./rismile/%%c /webs/%%c
for %%c in (./rismile/images/*.*) do tftp -i %1 PUT ./rismile/images/%%c /webs/images/%%c
for %%c in (./rismile/gwt/standard/*.*) do tftp -i %1 PUT ./rismile/gwt/standard/%%c /webs/gwt/standard/%%c
for %%c in (./rismile/gwt/standard/images/*.*) do tftp -i %1 PUT ./rismile/gwt/standard/images/%%c /webs/gwt/standard/images/%%c
for %%c in (./rismile/gwt/standard/images/ie6/*.*) do tftp -i %1 PUT ./rismile/gwt/standard/images/ie6/%%c /webs/gwt/standard/images/ie6/%%c
