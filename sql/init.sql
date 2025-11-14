-- 审核记录表
CREATE TABLE audit_record (
    audit_id BIGSERIAL PRIMARY KEY,
    table_name VARCHAR(100) NOT NULL,
    record_id BIGINT NOT NULL,
    audit_status SMALLINT NOT NULL,
    audit_opinion TEXT,
    audit_by VARCHAR(50) NOT NULL,
    audit_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_approved BOOLEAN NOT NULL,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE audit_record IS '审核记录表';
COMMENT ON COLUMN audit_record.audit_id IS '审核记录ID';
COMMENT ON COLUMN audit_record.table_name IS '审核表名';
COMMENT ON COLUMN audit_record.record_id IS '记录ID';
COMMENT ON COLUMN audit_record.audit_status IS '审核状态：0-待审核，1-已通过，2-未通过';
COMMENT ON COLUMN audit_record.audit_opinion IS '审核意见';
COMMENT ON COLUMN audit_record.audit_by IS '审核人';
COMMENT ON COLUMN audit_record.audit_time IS '审核时间';
COMMENT ON COLUMN audit_record.is_approved IS '是否批准';
COMMENT ON COLUMN audit_record.is_deleted IS '是否删除';

-- 数据导入日志表
CREATE TABLE data_import_log (
    import_id BIGSERIAL PRIMARY KEY,
    import_file_name VARCHAR(200),
    import_type VARCHAR(50),
    import_status SMALLINT,
    import_result TEXT,
    import_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    imported_by VARCHAR(50),
    is_deleted INTEGER
);

COMMENT ON TABLE data_import_log IS '数据导入日志表';
COMMENT ON COLUMN data_import_log.import_id IS '导入记录ID';
COMMENT ON COLUMN data_import_log.import_file_name IS '导入文件名';
COMMENT ON COLUMN data_import_log.import_type IS '导入类型';
COMMENT ON COLUMN data_import_log.import_status IS '导入状态：0-失败，1-成功';
COMMENT ON COLUMN data_import_log.import_result IS '导入结果详情';
COMMENT ON COLUMN data_import_log.import_time IS '导入时间';
COMMENT ON COLUMN data_import_log.imported_by IS '导入人';
COMMENT ON COLUMN data_import_log.is_deleted IS '是否删除';

-- 家族成员信息表
CREATE TABLE family_member (
    member_id BIGINT PRIMARY KEY,
    tree_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    seniority_id BIGINT,
    gender SMALLINT,
    birth_date DATE,
    death_date DATE,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    remark TEXT,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE family_member IS '家族成员信息表';
COMMENT ON COLUMN family_member.member_id IS '成员ID';
COMMENT ON COLUMN family_member.tree_id IS '所属族谱树ID';
COMMENT ON COLUMN family_member.name IS '成员姓名';
COMMENT ON COLUMN family_member.seniority_id IS '辈分ID';
COMMENT ON COLUMN family_member.gender IS '性别：1-男，2-女，0-未知';
COMMENT ON COLUMN family_member.birth_date IS '出生日期';
COMMENT ON COLUMN family_member.death_date IS '死亡日期';
COMMENT ON COLUMN family_member.phone IS '联系电话';
COMMENT ON COLUMN family_member.email IS '电子邮箱';
COMMENT ON COLUMN family_member.address IS '地址';
COMMENT ON COLUMN family_member.remark IS '备注';
COMMENT ON COLUMN family_member.create_by IS '创建人';
COMMENT ON COLUMN family_member.create_time IS '创建时间';
COMMENT ON COLUMN family_member.update_by IS '修改人';
COMMENT ON COLUMN family_member.update_time IS '修改时间';
COMMENT ON COLUMN family_member.is_deleted IS '是否删除';

-- 族谱结构树表
CREATE TABLE family_tree (
    tree_id BIGINT PRIMARY KEY,
    tree_name VARCHAR(100) NOT NULL,
    description TEXT,
    root_member_id BIGINT,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE family_tree IS '族谱结构树表';
COMMENT ON COLUMN family_tree.tree_id IS '族谱树ID';
COMMENT ON COLUMN family_tree.tree_name IS '族谱名称';
COMMENT ON COLUMN family_tree.description IS '族谱描述';
COMMENT ON COLUMN family_tree.root_member_id IS '根节点成员ID';
COMMENT ON COLUMN family_tree.create_by IS '创建人';
COMMENT ON COLUMN family_tree.create_time IS '创建时间';
COMMENT ON COLUMN family_tree.update_by IS '修改人';
COMMENT ON COLUMN family_tree.update_time IS '修改时间';
COMMENT ON COLUMN family_tree.is_deleted IS '是否删除';

-- 多媒体资源表
CREATE TABLE media_resource (
    resource_id BIGSERIAL PRIMARY KEY,
    member_id BIGINT,
    file_name VARCHAR(200),
    file_path TEXT,
    file_type VARCHAR(20),
    file_size BIGINT,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    upload_by VARCHAR(50),
    description TEXT,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE media_resource IS '多媒体资源表';
COMMENT ON COLUMN media_resource.resource_id IS '资源ID';
COMMENT ON COLUMN media_resource.member_id IS '关联成员ID';
COMMENT ON COLUMN media_resource.file_name IS '文件名称';
COMMENT ON COLUMN media_resource.file_path IS '文件路径';
COMMENT ON COLUMN media_resource.file_type IS '文件类型（image/audio/video）';
COMMENT ON COLUMN media_resource.file_size IS '文件大小（字节）';
COMMENT ON COLUMN media_resource.upload_time IS '上传时间';
COMMENT ON COLUMN media_resource.upload_by IS '上传人';
COMMENT ON COLUMN media_resource.description IS '文件描述';
COMMENT ON COLUMN media_resource.is_deleted IS '是否删除';

-- 操作日志表
CREATE TABLE operation_log (
    log_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    operation_module VARCHAR(100) NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    operation_desc TEXT NOT NULL,
    operation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45) NOT NULL,
    user_agent TEXT NOT NULL,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE operation_log IS '操作日志表';
COMMENT ON COLUMN operation_log.log_id IS '日志ID';
COMMENT ON COLUMN operation_log.user_id IS '操作用户ID';
COMMENT ON COLUMN operation_log.operation_module IS '操作模块';
COMMENT ON COLUMN operation_log.operation_type IS '操作类型';
COMMENT ON COLUMN operation_log.operation_desc IS '操作描述';
COMMENT ON COLUMN operation_log.operation_time IS '操作时间';
COMMENT ON COLUMN operation_log.ip_address IS 'IP地址';
COMMENT ON COLUMN operation_log.user_agent IS '浏览器标识';
COMMENT ON COLUMN operation_log.is_deleted IS '是否删除';

-- 亲属关系表
CREATE TABLE relationship (
    relation_id BIGINT PRIMARY KEY,
    from_member_id BIGINT NOT NULL,
    to_member_id BIGINT NOT NULL,
    relation_type VARCHAR(50),
    note TEXT,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE relationship IS '亲属关系表';
COMMENT ON COLUMN relationship.relation_id IS '关系ID';
COMMENT ON COLUMN relationship.from_member_id IS '起始成员ID';
COMMENT ON COLUMN relationship.to_member_id IS '目标成员ID';
COMMENT ON COLUMN relationship.relation_type IS '关系类型（如父子、夫妻等）';
COMMENT ON COLUMN relationship.note IS '备注说明';
COMMENT ON COLUMN relationship.create_by IS '创建人';
COMMENT ON COLUMN relationship.create_time IS '创建时间';
COMMENT ON COLUMN relationship.update_by IS '修改人';
COMMENT ON COLUMN relationship.update_time IS '修改时间';
COMMENT ON COLUMN relationship.is_deleted IS '是否删除';

-- 统计分析报告表
CREATE TABLE statistics_report (
    report_id BIGSERIAL PRIMARY KEY,
    report_name VARCHAR(100),
    report_type VARCHAR(50),
    generate_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    generated_by VARCHAR(50),
    content JSONB
);

COMMENT ON TABLE statistics_report IS '统计分析报告表';
COMMENT ON COLUMN statistics_report.report_id IS '报告ID';
COMMENT ON COLUMN statistics_report.report_name IS '报告名称';
COMMENT ON COLUMN statistics_report.report_type IS '报告类型';
COMMENT ON COLUMN statistics_report.generate_time IS '生成时间';
COMMENT ON COLUMN statistics_report.generated_by IS '生成人';
COMMENT ON COLUMN statistics_report.content IS '报告内容JSON格式';

-- 角色信息表
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    description TEXT,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE sys_role IS '角色信息表';
COMMENT ON COLUMN sys_role.role_id IS '角色唯一标识';
COMMENT ON COLUMN sys_role.role_name IS '角色名称';
COMMENT ON COLUMN sys_role.description IS '角色描述';
COMMENT ON COLUMN sys_role.create_by IS '创建人';
COMMENT ON COLUMN sys_role.create_time IS '创建时间';
COMMENT ON COLUMN sys_role.update_by IS '修改人';
COMMENT ON COLUMN sys_role.update_time IS '修改时间';
COMMENT ON COLUMN sys_role.is_deleted IS '是否删除';

-- 用户信息表
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    status SMALLINT,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_by VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE sys_user IS '用户信息表';
COMMENT ON COLUMN sys_user.user_id IS '用户唯一标识';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.status IS '状态：1-启用，0-禁用';
COMMENT ON COLUMN sys_user.create_by IS '创建人';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.update_by IS '修改人';
COMMENT ON COLUMN sys_user.update_time IS '修改时间';
COMMENT ON COLUMN sys_user.is_deleted IS '是否删除';

-- 用户角色关联表
CREATE TABLE user_role_relation (
    relation_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    create_by VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE user_role_relation IS '用户角色关联表';
COMMENT ON COLUMN user_role_relation.relation_id IS '关联记录ID';
COMMENT ON COLUMN user_role_relation.user_id IS '用户ID';
COMMENT ON COLUMN user_role_relation.role_id IS '角色ID';
COMMENT ON COLUMN user_role_relation.create_by IS '创建人';
COMMENT ON COLUMN user_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN user_role_relation.is_deleted IS '是否删除';

-- 数据版本历史记录表
CREATE TABLE version_history (
    history_id BIGSERIAL PRIMARY KEY,
    table_name VARCHAR(100),
    record_id BIGINT,
    operation_type VARCHAR(20),
    before_data JSONB,
    after_data JSONB,
    operate_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operated_by VARCHAR(50),
    is_deleted INTEGER NOT NULL
);

COMMENT ON TABLE version_history IS '数据版本历史记录表';
COMMENT ON COLUMN version_history.history_id IS '版本历史ID';
COMMENT ON COLUMN version_history.table_name IS '操作表名';
COMMENT ON COLUMN version_history.record_id IS '记录ID';
COMMENT ON COLUMN version_history.operation_type IS '操作类型（INSERT/UPDATE/DELETE）';
COMMENT ON COLUMN version_history.before_data IS '操作前数据';
COMMENT ON COLUMN version_history.after_data IS '操作后数据';
COMMENT ON COLUMN version_history.operate_time IS '操作时间';
COMMENT ON COLUMN version_history.operated_by IS '操作人';
COMMENT ON COLUMN version_history.is_deleted IS '是否删除';
