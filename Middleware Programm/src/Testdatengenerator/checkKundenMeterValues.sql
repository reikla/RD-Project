-- Kunden - Meter - Values
SELECT 
    a.customer_id,
    a.firstname,
    a.lastname,
    b.id_meter,
    COUNT(*),
    MIN(c.power_p1),
    MAX(c.power_p1),
    COUNT(*)
FROM
    customer a
        LEFT OUTER JOIN
    meter_management b ON b.id_customer = a.customer_id
        LEFT OUTER JOIN
    meter_data c ON c.meter_id = b.id_meter
GROUP BY a.customer_id , b.id_meter;