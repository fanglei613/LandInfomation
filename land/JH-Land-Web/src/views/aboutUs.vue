<template>
  <div class="content">
    <div class="banner">
      <div class="banner-con">
        <p>
          全国地块查询平台<br />
          湖北、安徽、河南、湖南已上线，<br />
          山东、黑龙江、新疆正在更新中...
        </p>
      </div>
    </div>
    <div class="intro">
      <div class="intro-item">
        <img
          src="../assets/images/about/info03.png"
          width="281"
          height="190"
          alt=""
        />
        <p>数据精准<br />亚米级数据</p>
      </div>
      <div class="intro-item">
        <img
          src="../assets/images/about/info02.png"
          width="273"
          height="166"
          alt=""
        />
        <p>人工智能<br />AI+深度学习</p>
      </div>
      <div class="intro-item">
        <img
          src="../assets/images/about/info01.png"
          width="269"
          height="168"
          alt=""
        />
        <p>多维度信息<br />作物、长势、估产、气象...</p>
      </div>
    </div>
    <div class="info">
      <h4>珈和简介</h4>
      <p>
        武汉珈和科技有限公司（简称“珈和科技”）成立于2013年，致力于空间智能技术为核心的大数据信息服务，创新技术依托于中国科学院、武汉大学、北京师范大学，是业内领先的卫星遥感解译服务商。公司总部坐落于武汉国家地球空间信息产业化基地，已在北京、重庆、辽宁、新疆、山西、山东、江苏、福建等地设立了分公司和办事处，形成了健全的市场服务体系。
        <br />珈和科技秉承“提升效能、优化生态”的企业使命，潜心耕耘遥感信息行业多年。服务领域涵盖遥感影像私有云、遥感影像数据挖掘、农业遥感行业应用等。公司通过遥感测量技术、GPS
        定位技术、GIS
        地理信息平台、作物生长趋势模拟技术、数据挖掘技术、开源数据库、以及API
        云存储技术等，对地球卫星影像进行加工处理和数据分析，能够提供包括作物种植分布提取、农作物长势监测、产量预估、气象跟踪、旱涝预警、灾后估损、种植选地、种植计划等数据采集和分析的智慧农业解决方案，真正的实现精准农业和农业现代化。
        <br />珈和农情遥感监测辐射范围覆盖全球，业务范围涉及小麦、水稻、玉米、棉花、大豆等多类世界性流通大宗农作物数据提取、农林资源普查、农业环境污染监测等应用领域，能够为种植、投资、贸易、环保等企业事业单位提供科学的数据和决策支持。
        <br />公司具备国家测绘部门认证颁发的乙级测绘资质，并通过ISO9001质量体系认证，拥有多项发明专利及软件著作权，是国家认定的高新技术企业，是武汉市大数据企业，也是武汉市东湖企业信用促进会员。
      </p>
    </div>
    <div class="footer">
      <div class="footer-h clearfix">
        <div class="contract">
          <h4>联系方式</h4>
          <ul>
            <li>电话：027-81562688</li>
            <li>邮箱：Market@datall.cn</li>
            <li>Q Q：2357667118</li>
            <li>Q Q群：105953373</li>
            <li>地址：湖北省武汉光谷武大园路武大科技园2期B3栋6088室</li>
          </ul>
        </div>
        <div class="location">
          <img
            src="../assets/images/about/location.png"
            width="460"
            height="197"
            alt="珈和位置"
          />
        </div>
        <div class="code">
          <img
            src="../assets/images/about/ico_wb.png"
            width="100"
            height="100"
            alt="微博二维码"
          />
          <p>官网微博</p>
        </div>
        <div class="code code-wx">
          <img
            src="../assets/images/about/ico_wx.png"
            width="100"
            height="100"
            alt="微信二维码"
          />
          <p>官方微信</p>
        </div>
      </div>
      <p class="copyright">
        鄂公网安备 42018502001246号 鄂ICP备13010685号
        Copyright©武汉珈和科技有限公司 All Rights Reserved
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "about"
};
</script>

<style lang="less" scoped>
.content {
  width: 100%;
  height: 100%;
  background: #ffffff;
  overflow: auto;
  .banner {
    width: 100%;
    height: 350px;
    padding: 0 5% 0 13%;
    background: linear-gradient(
      90deg,
      rgba(11, 66, 245, 1) 0%,
      rgba(52, 227, 255, 1) 100%
    );
    .banner-con {
      display: flex;
      align-items: center;
      width: 100%;
      height: 100%;
      background: url("../assets/images/about/banner-bg.png") no-repeat;
      background-size: 666px 324px;
      background-position: right center;
      p {
        font-size: 24px;
        font-weight: bold;
        color: #ffffff;
        line-height: 44px;
      }
    }
  }
  .intro {
    display: flex;
    width: 100%;
    .intro-item {
      flex: 1;
      display: inline-flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
      height: 484px;
      padding: 100px 0 78px;
      border-right: 1px solid #dadada;
      &:last-child {
        border-right: none;
      }
      p {
        font-size: 24px;
        font-weight: 400;
        line-height: 36px;
        text-align: center;
      }
    }
  }
  .info {
    width: 100%;
    height: 430px;
    background: url("../assets/images/about/info-bg.png") no-repeat;
    background-size: 100% 430px;
    overflow: hidden;
    h4 {
      margin-top: 40px;
      font-size: 22px;
      font-weight: bold;
      color: #ffffff;
      text-align: center;
    }
    p {
      margin-top: 50px;
      padding: 0 80px;
      font-size: 14px;
      color: #ffffff;
      line-height: 30px;
    }
  }
  .footer {
    width: 100%;
    overflow: hidden;
    .footer-h {
      margin-top: 40px;
      padding: 0 80px;
      .contract {
        float: left;
        margin-right: 50px;
        h4 {
          font-size: 14px;
        }
        ul {
          margin-top: 15px;
          font-size: 12px;
          color: #707070;
          line-height: 30px;
        }
      }
      .location {
        float: left;
      }
      .code {
        float: right;
        &.code-wx {
          margin-right: 15px;
        }
        p {
          padding-top: 10px;
          font-size: 12px;
          text-align: center;
        }
      }
    }
    p.copyright {
      margin: 40px 0 15px 0;
      font-size: 12px;
      color: #999999;
      text-align: center;
    }
  }
}
</style>
