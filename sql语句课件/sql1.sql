-- 注释
-- ctrl+shift+r  运行选中的内容
-- ＝，!＝,<>，<,>,<=,>=,        -- any,some,all   等到讲到子查询的时候学习
-- is null,is not null
-- between x and y
-- in（list），not in（list）
-- like  _ ,%


select * from emp;
-- 查询工资为1600的员工信息
select * from emp where sal=1600;

-- 查询工资不为1600的员工信息
select * from emp where sal!=1600;
select * from emp where sal<>1600;

-- 查询工资3000及以上的员工信息
select * from emp where sal>=3000;

-- 查询员工奖金为null的员工信息
select * from emp where comm is null;

-- 查询员工奖金不为null的员工信息
select * from emp where comm is not null;

-- 查询工资为1100到1600的员工信息
select * from emp where sal>=1100 and sal<=1600;
select * from emp where sal between 1100 and 1600;


-- 查询工资为1250 或者1100 或者1500的员工信息
select * from emp where sal=1100 or sal=1250 or sal=1500;
select * from emp where sal in (1100,1250,1500);


-- 查询工资不为1250 不为1100 不为1500的员工信息
select * from emp where sal<>1100 and sal<>1250 and sal<>1500;
select * from emp where sal not in(1100,1250,1500);

-- like   模糊查询
-- 查询员工名字中有A的员工信息
select * from emp where ename like "A";   --   等效于  select * from emp where ename = "A";

-- like 语句必须要配上 _  或者 % 才有意义
-- _   代表占，并且只占一位
-- 查询员工姓名为5个字母并且第3个字母是A的员工信息
select * from emp where ename like '__A__';

-- %   代表可以不占，也可以占无数位
-- 查询员工姓名以A开头的所有员工的信息
select * from emp WHERE ename like 'a%';



