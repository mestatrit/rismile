for %%c in (./rismile/*.*) do tftp -i %1 PUT ./rismile/%%c /webs/%%c
for %%c in (./rismile/images/*.*) do tftp -i %1 PUT ./rismile/images/%%c /webs/images/%%c
