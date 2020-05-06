一、
1,
select*from emp;
--(1)查询DEPT表显示所有部门名称.
select dname from dept;
(2)查询EMP表显示所有雇员名及其全年收入(月收入=工资+补助),处理NULL行,并指定列别名为"年收入"。
select ename,(sal+ifall(sal,0))*24 '年收入' from emp where comm ifnull();
(3)查询显示不存在雇员的所有部门号。
select d.* from dept d where d.deptno not in (select distinct e.deptno from emp e);
2,
--(1)查询EMP表显示工资超过2850的雇员姓名和工资。
select ename,sal from emp where sal>2850;
--(2)查询EMP表显示工资不在1500～2850之间的所有雇员及工资。
select ename,sal from emp where sal<1500 or sal>2850;
--(3)查询EMP表显示代码为7566的雇员姓名及所在部门代码。
select ename,deptno from emp where empno=7566;
--(4)查询EMP表显示部门10和30中工资超过1500的雇员名及工资。
select ename,sal from emp where deptno in (10,30) and sal>1500;
--(5)查询EMP表显示第2个字符为"A"的所有雇员名其工资。
select ename,sal from emp where instr(ename,'a')=2;
select ename,sal from emp where ename like '_a%';
--(6)查询EMP表显示补助非空的所有雇员名及其补助。
select ename,comm from emp where comm is not null; 


3,
select*from emp;
--(1)查询EMP表显示所有雇员名、工资、雇佣日期，并以雇员名的升序进行排序。
select ename,sal,hiredate from emp order by ename asc;
--(2)查询EMP表显示在1981年2月1日到1981年5月1日之间雇佣的雇员名、岗位及雇佣日期，并以雇佣日期进行排序。
select ename,job,hiredate from emp where hiredate>'1981-2-1' and hiredate<'1981-5-1' order by hiredate asc ;
--(3)查询EMP表显示获得补助的所有雇员名、工资及补助，并以工资升序和补助降序排序。
select ename,sal,comm from emp order by sal asc,comm desc;


二、
select*from emp;
--1．列出至少有一个雇员的所有部门。
select d.dname from emp e,dept d where e.deptno=d.deptno;
--2．列出薪金比“SMITH”多的所有雇员。
select*from emp where sal>(select sal from emp where ename='smith');
--3．列出所有雇员的姓名及其上级的姓名。
select e1.ename,e2.ename from emp e1 left join emp e2 on e1.mgr=e2.empno;
4．列出入职日期早于其直接上级的所有雇员。
select ename from emp where hiredate<all(select distinct e1.hiredate from emp e1,emp e2 where e1.empno=e2.mgr);
--5．列出部门名称和这些部门的雇员，同时列出那些没有雇员的部门。 
select*from emp e right join dept d on e.deptno=d.deptno;   
--6．列出所有“CLERK”（办事员）的姓名及其部门名称。
select e.ename,d.dname from emp e,dept d where e.deptno=d.deptno and job='clerk';
7．列出各种岗位的最低薪金，并显示最低薪金大于1500所有工作岗位及其最低薪资。
select job,min(sal)from emp group by job having min(sal)>1500;
select min(sal) from emp;
select job,min(sal) from emp group by job having min(sal)>1500;

--8．列出从事“SALES”（销售）工作的雇员的姓名，假定不知道销售部的部门编号。
select ename from emp where job='salesman';
--9．列出薪金高于公司平均的所有雇员。
select ename,sal from emp where sal>(select avg(sal) from emp);
--10．列出与“SCOTT”从事相同工作的所有雇员。
select*from emp where job=(select job from emp where ename='scott');
--11．列出薪金等于在部门30工作的所有雇员的薪金的雇员的姓名和薪金。
select ename,sal from emp where sal in(select sal from emp where deptno=30);
--12．列出薪金高于在部门30工作的所有雇员的薪金的雇员的姓名和薪金。
select ename,sal from emp where sal>all(select sal from emp where deptno=30);
13．列出在每个部门工作的雇员的数量以及其他信息。
select count(e.deptno),e.* from emp e group by deptno;
--14．列出所有雇员的雇员名称、部门名称和薪金。
select e.ename,d.dname,e.sal from emp e,dept d where e.deptno=d.deptno;
--16．列出分配有雇员数量的所有部门的详细信息即使是分配有0个雇员。
select*from emp e right join dept d on e.deptno=d.deptno;
--17．列出各种类别工作的最低工资。
select min(sal) from emp group by job;
--18．列出各个部门的MANAGER（经理）的最低薪金。
select min(sal)from emp where job='manager' group by deptno ; 
--19．列出按计算的字段排序的所有雇员的年薪。
select sal*12 from emp order by sal*12 asc;


