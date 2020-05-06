-- order by 排序
-- 默认升序      显性升序  asc     降序  desc

-- 查询所有员工的信息，并且以工资进行排序
select * from emp order by sal ;
select * from emp order by sal asc;  -- 升序
select * from emp order by sal desc;   -- 降序


-- 查询所有员工的信息，并且以入职日期进行排序
select * from emp order by hiredate;
select * from emp order by hiredate desc;



-- order by 多个字段
-- 会先将前面的字段进行排序，如果前面字段有相同值才考虑第二个条件。
-- 按工资和入职日期进行排序

select * from emp order by sal,hiredate;
select * from emp order by sal,hiredate desc;


-- 要求查询员工信息，以部门的升序，工资的降序进行排序
select * from emp order by deptno asc,sal;

-- 计算字段
-- 要求查询工资上涨500之后大于等于3000的员工的编号、姓名、工资、上涨之后的工资
select empno,ename,sal,sal+500 from emp where sal+500>=3000 and sal<3000;


-- 单行函数
select lower("ABC") from dual;
-- 查询所有员工的姓名，并且以小写的形式显示
select lower(ename) from emp;

select concat("ABC","DEF") from dual;

-- 查询员工的编号、姓名   要求编号以   No.xxxx形式显示
select concat("No.",empno),ename from emp;


select sysdate() from dual;


--  组函数
--  max 最大值     min最小值     avg 平均值     count 个数   sum  求和

-- 求出公司的总工资支出
select sum(sal) from emp;

-- 求出最高工资
select max(sal) from emp;

-- 求出最低工资
select min(sal) from emp;


-- 求出平均工资
select avg(sal) from emp;


-- 求出公司的总人数
-- 使用count的时候  推荐使用  count(主键)  或者是 count(*)
select count(*) from emp;
select count(empno) from emp;


-- 查询员工平均奖金
select ifnull(comm,0) from emp;

select avg(ifnull(comm,0)) from emp;



-- group by  以什么进行分组 
-- where  行级过滤
-- having 组级过滤        如果判断的条件有组函数则只能使用having语法
-- select distinct  字段1,字段2... from 表名 where 行级条件 group by 分组 having 组级条件 order by 排序
-- 查询各个部门的部门编号和平均工资
select deptno,avg(sal) from emp group by deptno;

-- 查询各种岗位的岗位名字及最高工资
select * from emp order by job,sal desc;

select job,max(sal) from emp group by job;

-- 查询平均工资要在2000以上的每个部门的编号及平均工资
select deptno,avg(sal) from emp where avg(sal)>=2000 group by deptno;  -- 错误，where 不能判断组函数
select deptno,avg(sal) from emp group by deptno having avg(sal)>=2000; 













