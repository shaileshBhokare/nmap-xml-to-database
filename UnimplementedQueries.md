#Unimplemented queries

# Queries which could be implemented #

SELECT **FROM host\_address h, open\_ports op WHERE h.host\_id = op.hostId AND h.type = 'ipv4' AND h.host\_id = 5;**

SELECT **FROM open\_ports o;**

SELECT address, count(op.hostId) as AntallÅpne, op.hostId FROM host\_address h, open\_ports op where h.host\_id = op.hostId AND h.type = 'ipv4' GROUP BY h.address ORDER BY hostId;

SELECT **FROM open\_ports o WHERE hostId = 10;**

SELECT op.**, h.** FROM open\_ports op, host\_address h where op.hostId = h.host\_id AND h.type = 'ipv4' GROUP BY h.address;

SELECT **FROM host\_address h where address = '217.13.4.50';
SELECT COUNT(**) FROM open\_ports op WHERE hostId = 12;