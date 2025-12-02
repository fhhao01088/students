INSERT INTO role (id, code, name) VALUES
    (1, 'ADMIN', 'Administrator'),
    (2, 'ASSET_ADMIN', 'Asset Administrator'),
    (3, 'USER', 'Standard User')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO `user` (id, username, password, name, status, role_id) VALUES
    (1, 'admin', 'admin123', 'System Administrator', 'ACTIVE', 1),
    (2, 'assetadmin', 'asset123', 'Asset Administrator', 'ACTIVE', 2),
    (3, 'user1', 'user123', 'User One', 'ACTIVE', 3)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    password = VALUES(password),
    status = VALUES(status),
    role_id = VALUES(role_id);
