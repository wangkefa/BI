import React, { useState, useEffect } from 'react';
import { Descriptions, Button, message, Space } from 'antd';
import ProCard from '@ant-design/pro-card';
import {
  ProForm,
  ProFormText,
  ProFormUploadButton,
  ProFormSelect,
  ProFormTextArea,
  ProFormDatePicker,
} from '@ant-design/pro-form';
import { UploadOutlined } from '@ant-design/icons';
import { getLoginUserUsingGET, updateUserUsingPOST } from '@/services/yubi/userController';

const PersonalCenter = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [userInfo, setUserInfo] = useState({
    name: 'John Doe',
    userAvatar: '/default-avatar.jpg',
    userInfo: 'Some user info',
    password: '******',
    createTime: '2023-01-15 14:30:00',
  });

  useEffect(() => {
    // Fetch user information from the server
    // You can replace this with your actual data fetching logic
    // For now, set default values
    const fetchUserInfo = async () => {
      try {
        const res = await getLoginUserUsingGET();
        setUserInfo(res.data);
      } catch (error) {
        // 处理获取用户信息失败的情况
        console.error('获取用户信息失败', error);
      }
    };

    fetchUserInfo();
  }, []);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleSaveClick = async (values) => {
    try {
      // 模拟更新用户信息
      const updatedUserInfo = { ...userInfo, ...values };

      // 将更新后的用户信息发送到服务器
      await updateUserUsingPOST(updatedUserInfo);

      setIsEditing(false);
      setUserInfo(updatedUserInfo);
      message.success('用户信息更新成功');
    } catch (error) {
      console.error('保存用户信息失败', error);
    }
  };

  const handleCancelClick = () => {
    setIsEditing(false);
  };



  const handleFileChange = ({ file }) => {
    if (file.status === 'done') {
      message.success(`${file.name} 上传成功`);
    } else if (file.status === 'error') {
      message.error(`${file.name} 上传失败`);
    }
  };

  return (
    <ProCard>
      <Descriptions title="Personal Information" bordered layout="vertical">
        <Descriptions.Item label="Name">
          {isEditing ? (
            <ProFormText name="name" readonly={false} />
          ) : (
            userInfo.name
          )}
        </Descriptions.Item>
        <Descriptions.Item label="Avatar">
          {isEditing ? (
            <ProFormUploadButton
              name="userAvatar"
              readonly={false}
              customRequest={({ file, onSuccess }) => {
                const formData = new FormData();
                formData.append('file', file);
                // 模拟上传文件
                setTimeout(() => {
                  onSuccess();
                }, 1000);
              }}
              showUploadList={false}
              beforeUpload={() => false}
              onChange={handleFileChange}
            >
              <Button icon={<UploadOutlined />}>上传头像</Button>
            </ProFormUploadButton>
          ) : (
              <img src={userInfo.userAvatar} alt="Avatar" style={{ height: '100px' }} />
          )}
        </Descriptions.Item>
        <Descriptions.Item label="User Info">
          {isEditing ? (
            <ProFormText name="userInfo" readonly={false} />
          ) : (
            userInfo.userInfo
          )}
        </Descriptions.Item>
        <Descriptions.Item label="Password">
          {isEditing ? (
            <ProFormText.Password name="password" readonly={false} />
          ) : (
            '******'
          )}
        </Descriptions.Item>
        <Descriptions.Item label="Create Time" span={2}>
          {new Date(userInfo.createTime).toLocaleString()}
        </Descriptions.Item>
      </Descriptions>

      {!isEditing && (
        <Space>
          <Button type="primary" onClick={handleEditClick}>
            编辑
          </Button>
        </Space>
      )}
      {isEditing && (
        <ProForm.Group>
          <Space style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
            <Button type="primary" onClick={handleCancelClick}>
              取消
            </Button>
            <Button type="primary" htmlType="submit">
              保存
            </Button>
          </Space>
        </ProForm.Group>
      )}
    </ProCard>
  );
};

export default PersonalCenter;
