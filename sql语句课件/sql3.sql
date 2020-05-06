-- 多表查询

-- 查询员工信息及其部门的所有信息
select * from emp;
select * from dept;

select * from emp,dept ;   -- 笛卡尔积


-- 等值连接
-- 消除笛卡尔积
select * from emp e,dept d where e.deptno=d.deptno;

-- 查询名叫‘'JAMES’的员工的员工编号，姓名和部门编号，部门的名字
select e.empno,e.ename,d.deptno,d.dname from emp e,dept d where e.deptno=d.deptno and e.ename="james";

-- 非等值连接
-- 查询所有员工的员工信息和工资等级	
select * from emp;
select * from salgrade;
select e.*,s.grade from emp e,salgrade s where e.sal between s.losal and s.hisal;

-- 多余两张表的连接
-- 查询员工的员工编号和姓名，部门名字，工资等级

select e.empno,e.ename,d.dname,s.grade from emp e,dept d,salgrade s where e.deptno=d.deptno and e.sal between s.losal and s.hisal;







