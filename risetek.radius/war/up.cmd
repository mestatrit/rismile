for %%c in (./rismile/*.*) do tftp -i %1 PUT ./rismile/%%c /webs/%%c
for %%c in (./rismile/images/*.*) do tftp -i %1 PUT ./rismile/images/%%c /webs/images/%%c
for %%c in (./rismile/images/ie6/*.*) do tftp -i %1 PUT ./rismile/images/ie6/%%c /webs/images/ie6/%%c
