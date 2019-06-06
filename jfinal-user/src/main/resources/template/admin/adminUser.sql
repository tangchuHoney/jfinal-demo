#namespace("admin.user")
    #sql("queryByUserName")
    select * from sys_user where user_name= ?
    #end
#end

