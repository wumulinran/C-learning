-- 交叉连接
-- cross join
-- 查询所有员工信息及部门信息
select * from emp e,dept d where e.deptno=d.deptno;

select * from emp e cross join dept d on e.deptno=d.deptno;
-- 查询名叫allen的员工信息及部门信息
select * from emp e cross join dept d on e.deptno=d.deptno where e.ename="allen";


-- 自然连接
-- NATURAL JOIN
-- 条件1)两张表必须具备同名字的一个字段    2)该字段还要同类型    3)该字段还要具备相同的值
desc emp;
desc dept;
select * from emp;
select * from dept;

select * from emp e natural join dept d;



-- 左连接 left join
-- 右连接 right join
-- 查询所有部门下的所有员工信息
select d.*,e.* from emp e,dept d where e.deptno=d.deptno;

select d.*,e.* from emp e right join dept d on e.deptno=d.deptno;
select d.*,e.* from dept d left join emp e on e.deptno=d.deptno;


-- 自连接  (是一种查询的思想)
-- 查询每个员工的编号和姓名和管理者编号和姓名
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1,emp e2 where e1.mgr=e2.empno;    --有问题，会丢失一条信息，由于BOSS没有被管理
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1 left join emp e2 on e1.mgr=e2.empno;

-- 全连接
-- 查询所有员工和所有部门的信息
select e.*,d.* from emp e left join dept d on e.deptno=d.deptno;
select e.*,d.* from emp e right join dept d on e.deptno=d.deptno;

select e.*,d.* from emp e full join dept d on e.deptno=d.deptno;   --  mysql 没有  full join 语法
 
-- 模拟全连接
select e.*,d.* from emp e left join dept d on e.deptno=d.deptno
union
select e.*,d.* from emp e right join dept d on e.deptno=d.deptno;





-- UNION
select * from emp
union
select * from emp2;

-- UNION ALL

select * from emp
union all
select * from emp2;


-- 子查询

-- 单行子查询

-- 查询有哪些人的工资是在整个雇员的平均工资之上
-- 第一步  查出平均工资
select avg(e1.sal) from emp e1;  -- 2031.6667

-- 第二步  找出哪些人在平均工资之上
select e2.* from emp e2 where e2.sal>2031;

-- 使用子查询做到上面的效果
select e2.* from emp e2 where e2.sal>(select avg(e1.sal) from emp e1);


-- 要求查出工资比allen高的所有员工信息
-- 查询allen的工资
select e1.sal from emp e1 where e1.ename="allen";
-- 查询比allen高的员工
select e2.* from emp e2 where e2.sal>1600;

-- 子查询
select e2.* from emp e2 where e2.sal>(select e1.sal from emp e1 where e1.ename="allen");


-- 查询和allen同岗位的员工信息
select e2.* from emp e2 where e2.job=(select e1.job from emp e1 where e1.ename="allen");


-- 多行子查询

-- 查在雇员中有哪些人是领导
-- 查询领导的编号
select distinct e1.mgr from emp e1 where e1.mgr is not null;


-- 查询领导的信息
select e2.* from emp e2 where e2.empno in(7902,7698...);


-- 最终写法
select e2.* from emp e2 where e2.empno in(select distinct e1.mgr from emp e1 where e1.mgr is not null);




-- some any all
-- 找出部门编号为20的所有员工中收入最高的职员
select e2.* from emp e2 where e2.sal=(select max(e1.sal) from emp e1 where e1.deptno=20) and e2.deptno=20;



-- 解法2
-- all   表示必须全部满足
-- 先求出20部门的所有工资的可能性
select e2.* from emp e2 where e2.sal>=all(select distinct e1.sal from emp e1 where e1.deptno=20) and e2.deptno=20;


-- 求出20号部门工资不是最低的所有员工信息
select e2.* from emp e2 where e2.sal>(select min(e1.sal) from emp e1 where e1.deptno=20) and e2.deptno=20;


-- 解法2
-- some any

select e2.* from emp e2 where e2.sal>some(select distinct e1.sal from emp e1 where e1.deptno=20) and e2.deptno=20;
select e2.* from emp e2 where e2.sal>any(select distinct e1.sal from emp e1 where e1.deptno=20) and e2.deptno=20;





